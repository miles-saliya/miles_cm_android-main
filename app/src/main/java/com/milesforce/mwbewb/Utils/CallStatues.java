package com.milesforce.mwbewb.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.milesforce.mwbewb.Activities.EventTriggerActivity;
import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.Model.CallLogModel;
import com.milesforce.mwbewb.Model.EventModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_INCOMMING_EVENT;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_ONOUTGOING_CALL_EVENT;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_OUTGOING_EVENT;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_SUCCESS_CALL;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public abstract class CallStatues extends BroadcastReceiver {
    private static final String TAG = "PhoneStatReceiver";
    Realm realm;
    private static String incoming_number = null;
    ArrayList<CallLogModel> logModels = new ArrayList<>();
    ArrayList<CallLogModel> eventTriggerMode;
    String fromTime;
    String endTime;
    JSONObject jResult = new JSONObject();
    Handler h = new Handler();
    int delay = 900 * 1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    JSONArray jArray = new JSONArray();
    JSONArray traccker;
    Context context;
    SharedPreferences sharedPreferences, localTimeSharedPreference;
    SharedPreferences.Editor editor;
    Calendar cal;
    String accessToken;
    public String phoneNumber;
    SharedPreferences.Editor timeEditor;
    boolean Url_check_once = false;
    boolean state_callState = false;
    boolean outGoing_call_trigger = false;
    private static String mLastState;
    ApiClient apiClient;
    GetCallLogService getCallLogService;
    UserToken userToken;


    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SaveToken, MODE_PRIVATE);
        localTimeSharedPreference = context.getSharedPreferences("timestamp", MODE_PRIVATE);
        timeEditor = localTimeSharedPreference.edit();
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
        }

      //  accessToken = sharedPreferences.getString(AccessToken, null);
        // realm = Realm.getDefaultInstance();
//        handleStates(intent);
        String savedNumber;
        accessToken = userToken.getAccessToken();

        /*If we want to use App for K8 need to comment if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL") && !IS_OUTGOING_EVENT) this condition and unCommented   else if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && !IS_INCOMMING_EVENT) */

        /*if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL") && !IS_OUTGOING_EVENT) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            if (savedNumber != null) {
                Log.d("CallStatues-action", "NEW_OUTGOING_CALL : " + savedNumber);
                IS_OUTGOING_EVENT = true;
                onOutgoingEvent(savedNumber);
            }
        } else {*/
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        int state;
        if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE) && (IS_INCOMMING_EVENT || IS_OUTGOING_EVENT)) {
            state = TelephonyManager.CALL_STATE_IDLE;
            Log.d("CallStatues-action", "CALL_STATE_IDLE : " + state);
            IS_INCOMMING_EVENT = false;
            IS_OUTGOING_EVENT = false;
            onEndEvent();
            IS_ONOUTGOING_CALL_EVENT = false;
           // onCallEndded(context);
            if (!IS_SUCCESS_CALL) {
                openAlertDialog(context);
                Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();
                //  IS_SUCCESS_CALL = true;
            }

        } else if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) && !IS_INCOMMING_EVENT) {
            state = TelephonyManager.CALL_STATE_RINGING;
            Log.d("CallStatues-action", "CALL_STATE_RINGING : " + state);
            String mobile = intent.getStringExtra("incoming_number");
            if (mobile != null) {
                onIncomingEvent(mobile);
                IS_INCOMMING_EVENT = true;
            }
        } else if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && !IS_INCOMMING_EVENT) {
            state = TelephonyManager.CALL_STATE_OFFHOOK;
            Log.d("CallStatues-action", "EXTRA_STATE_OFFHOOK : " + state);
            String outgoingMobile = intent.getStringExtra("incoming_number");
            Log.d("CallStatues-action", "EXTRA_STATE_OFFHOOK : " + outgoingMobile);
            if (outgoingMobile != null) {
                onOutgoingEvent(outgoingMobile);
                IS_INCOMMING_EVENT = true;
            }
        }
        /* }*/
    }

//    protected abstract void onCallRecievedCallReceived(Context context, String number, String start);
//
//    protected abstract void onCallEndded(Context context);

    private void onOutgoingEvent(String mobile) {
        if (!IS_ONOUTGOING_CALL_EVENT) {
            String whereValue = String.valueOf(1000 * (getTodayTimestamp() / 1000));
            long unixTime = System.currentTimeMillis();
            String eventTime = String.valueOf(unixTime);
            timeEditor.putString("eventTime", whereValue);
            timeEditor.putString("mobileNumber", mobile);
            timeEditor.commit();
            CallTriggerEvent("OUTGOING", mobile, whereValue, "OUTGOING");
            IS_ONOUTGOING_CALL_EVENT = true;
            ConstantUtills.FILE_SAVE_TIME = whereValue;
         //  onCallRecievedCallReceived(context, mobile, whereValue);
        }

    }

    private void onIncomingEvent(String mobile) {
        if (!IS_ONOUTGOING_CALL_EVENT) {
            String whereValue = String.valueOf(1000 * (getTodayTimestamp() / 1000));
            timeEditor.putString("eventTime", whereValue);
            timeEditor.putString("mobileNumber", mobile);
            timeEditor.commit();
            ConstantUtills.FILE_SAVE_TIME = whereValue;
            CallTriggerEvent("Ringing", mobile, whereValue, "INCOMING");
            IS_ONOUTGOING_CALL_EVENT = true;
         // onCallRecievedCallReceived(context, mobile, whereValue);
        }
    }

    private void onEndEvent() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (localTimeSharedPreference.getString("eventTime", null) != null) {
                    getCallLogService = new GetCallLogService();
                    getCallLogService.getCallLog(context);
                }
            }
        }, 3000);
    }


    private void CallTriggerEvent(String ringing, String phoneNumber, String timeEvent, String
            directory) {
        eventTriggerMode = new ArrayList<>();
        CallLogModel callLogModel = new CallLogModel();
        callLogModel.setMobileNumber(phoneNumber);
        callLogModel.setCallType(ringing);
        callLogModel.setTallied_id(timeEvent);
        callLogModel.setDirectory_mode(directory);
        callLogModel.setEvent("start");
        callLogModel.setTimeStamp(timeEvent);
        callLogModel.setCallDuration("0");
        callLogModel.setCallDate(timeEvent);
        eventTriggerMode.add(callLogModel);
        EventModelToJson(eventTriggerMode);
    }

    private void EventModelToJson(ArrayList<CallLogModel> eventTriggerMode) {
        BatteryModel batteryModel = new BatterPercentage().getBattertPercentage(context);
        if (isConnected()) {
            traccker = new JSONArray();
            JSONObject jGroup = new JSONObject();// /sub Object
            try {
                for (int i = 0; i < eventTriggerMode.size(); i++) {
                    jGroup.put("phone_number", eventTriggerMode.get(i).getMobileNumber());
                    jGroup.put("directory", eventTriggerMode.get(i).getDirectory_mode());
                    jGroup.put("call_duration", eventTriggerMode.get(i).getCallDuration());
                    jGroup.put("time", eventTriggerMode.get(i).getTimeStamp());
                    jGroup.put("event", eventTriggerMode.get(i).getEvent());
                    jGroup.put("battery_percentage", batteryModel.getBattey_percentage());
                    jGroup.put("battery_status", batteryModel.getCharging_status());
                    jGroup.put("version_number", VERSION_NUMBER);
                  /*  jGroup.put("tallied_id", eventTriggerMode.get(i).getTallied_id());
                    jGroup.put("call_type", eventTriggerMode.get(i).getCallType());
                    jGroup.put("call_date", eventTriggerMode.get(i).getCallDate());
                    jGroup.put("old_data", "false");*/
                    /**/
                    traccker.put(jGroup);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("excep", e.getMessage());
            }
            SendTriggerDataToserver(traccker, accessToken);
        } else {
            openAlertDialog(context);
        }
    }

    private void SendTriggerDataToserver(JSONArray jArray, final String accessToken) {
        apiClient = ApiUtills.getAPIService();
        apiClient.sendLatestCallLog(jArray, "Bearer " + accessToken, "application/json").enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            IS_SUCCESS_CALL = false;
                        }
                    } else {
                        if (response.body().getStatus().equals("success")) {
                            String res = response.body().toString();
//                            if (response.body().getContact_type().equals("B2C") || response.body().getContact_type().equals("B2BIR") || response.body().getContact_type().equals("B2BCR")) {
//                                Toast.makeText(context, "Data Send Successfully", Toast.LENGTH_SHORT).show();
//                                Intent it = new Intent("intent.my.action");/*"intent.my.action"*/
//                                it.putExtra("engagement", "engagement");
//                                it.putExtra("person_id", response.body().getContact_details().getData().getPerson_id());
//                                it.putExtra("can_id", response.body().getContact_details().getData().getCan_id());
//                                it.putExtra("previousEngagement", response.body().getContact_details().getData().getEngagement_details());
//                                it.putExtra("courses", response.body().getContact_details().getData().getCourses());
//                                it.putExtra("levels", response.body().getContact_details().getData().getLevel());
//                                it.putExtra("mobile_id", response.body().getContact_details().getMobile_id());
//                                it.putExtra("person_name", response.body().getContact_details().getData().getPerson_name());
//                                it.putExtra("token_new", accessToken);
//                                it.setComponent(new ComponentName(context.getPackageName(), EventTriggerActivity.class.getName()));
//                                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(it);
//                                if (context instanceof Activity) {
//                                    ((Activity) context).finish();
//                                }
//
//
//                            }

                            IS_SUCCESS_CALL = true;
                        } else {
                            openAlertDialog(context);
                            IS_SUCCESS_CALL = false;
                        }
                    }
                } catch (Exception e) {
                    openAlertDialog(context);
                    IS_SUCCESS_CALL = false;
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                //  t.getMessage().toString();
                openAlertDialog(context);
                IS_SUCCESS_CALL = false;
            }
        });
    }

    private void openAlertDialog(Context context) {
        Toast.makeText(context, "Failed to ping Server due to Network Error.Call will be Updated on the website in a few minutes..!", Toast.LENGTH_LONG).show();
    }


    public boolean isConnected() {
        ConnectivityManager mgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                return true;
            } else {
                //No internet
                return false;
            }
        } else {
            //No internet
        }
        return false;
    }

    public long getTodayTimestamp() {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c2.set(Calendar.MONTH, c1.get(Calendar.MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
        c2.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
        c2.set(Calendar.SECOND, c1.get(Calendar.SECOND));
        return c2.getTimeInMillis();
    }
}
