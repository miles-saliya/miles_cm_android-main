package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Toast;

import com.milesforce.mwbewb.Model.CallLogModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_SUCCESS_CALL;
import static com.milesforce.mwbewb.Utils.ConstantUtills.LastRecordTimeCheck;
import static com.milesforce.mwbewb.Utils.ConstantUtills.UpdatedTimeRecord;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class GetCallLogService {
    Context context;
    SharedPreferences sharedPreferences, TimeSharedPreference;
    String AccessToken;
    SharedPreferences.Editor editor;
    JSONArray jArray;
    ArrayList<CallLogModel> logModels;
    JSONObject jResult = new JSONObject();
    ApiClient apiClient;
    Runnable callRunnable;
    Realm realm;
    UserToken userToken;

    public void getCallLog(Context context) {
        apiClient = ApiUtills.getAPIService();
        this.context = context;
        final Handler callHandler;
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
        }


      //  sharedPreferences = context.getSharedPreferences(ConstantUtills.SaveToken, MODE_PRIVATE);
      //  AccessToken = sharedPreferences.getString(ConstantUtills.AccessToken, null);
        AccessToken = userToken.getAccessToken();

        TimeSharedPreference = context.getSharedPreferences(LastRecordTimeCheck, MODE_PRIVATE);
        editor = TimeSharedPreference.edit();
        FetchAllCallLogData();
    }


    private void FetchAllCallLogData() {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (manager.getMode() == AudioManager.MODE_IN_CALL) {
        } else {
            if (TimeSharedPreference.getString(UpdatedTimeRecord, null) != null) {
                String data = TimeSharedPreference.getString(UpdatedTimeRecord, null);
                getCallLogs(String.valueOf(data));
                Log.d("shared", String.valueOf(data) + "shared");
            } else {
                long todayMidnightTimestamp = getMidnightTimestamp(new Date());
                String Mid_nightDate = String.valueOf(todayMidnightTimestamp);
                Log.d("shared", String.valueOf(Mid_nightDate) + "Mid");
                getCallLogs(Mid_nightDate);
            }

        }


    }

    private void getCallLogs(String mid_nightDate) {
        StringBuffer sb = new StringBuffer();
        logModels = new ArrayList<>();
        try {
            Long aSecondbeforeStart = Long.valueOf(mid_nightDate);
            String[] newDate = new String[]{String.valueOf(aSecondbeforeStart)};

            String strOrder1 = android.provider.CallLog.Calls.DATE + " desc";
            Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.DATE + " >= ?", newDate, strOrder1);

            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            sb.append("Call Details :");
            try {
                while (managedCursor.moveToNext()) {
                    String phNumber = managedCursor.getString(number);
                    String callType = managedCursor.getString(type);
                    String callDate = managedCursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String millisInString = dateFormat.format(callDayTime);
                    Log.d("epochCheck", millisInString);

                    Date convertedData = dateFormat.parse(millisInString);
                    long epoch = convertedData.getTime();


                    long output = callDayTime.getTime() / 1000;
                    String str = Long.toString(output);
                    long timestamp = Long.parseLong(str) * 1000;
                    String callDuration = managedCursor.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    CallLogModel callLogModel = new CallLogModel();
                    callLogModel.setMobileNumber(phNumber);
                    callLogModel.setCallDate(callDate);
                    callLogModel.setTimeStamp(String.valueOf(epoch));
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            callLogModel.setDirectory_mode(dir);
                            callLogModel.setCallDuration(callDuration);
                            logModels.add(callLogModel);
                            break;
                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            callLogModel.setDirectory_mode(dir);
                            callLogModel.setCallDuration(callDuration);
                            logModels.add(callLogModel);
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            callLogModel.setDirectory_mode(dir);
                            callLogModel.setCallDuration("0");
                            logModels.add(callLogModel);
                            break;
                        case CallLog.Calls.REJECTED_TYPE:
                            dir = "REJECTED";
                            callLogModel.setDirectory_mode(dir);
                            callLogModel.setCallDuration("0");
                            logModels.add(callLogModel);
                            break;
                    }
                }
                arrayToJson(logModels);
                managedCursor.close();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (jArray != null) {
            if (jArray.length() > 0) {
                sendDataToServer(AccessToken, jArray);
            }

        }
    }

    private JSONObject arrayToJson(ArrayList<CallLogModel> logModels) {
        BatteryModel batteryModel = new BatterPercentage().getBattertPercentage(context);
        jArray = new JSONArray();
        for (int i = 0; i < logModels.size(); i++) {
            JSONObject jGroup = new JSONObject();// /sub Object
            try {
                jGroup.put("phone_number", logModels.get(i).getMobileNumber());
                jGroup.put("directory", logModels.get(i).getDirectory_mode());
                jGroup.put("call_duration", logModels.get(i).getCallDuration());
                jGroup.put("time", logModels.get(i).getTimeStamp());
                jGroup.put("battery_percentage", batteryModel.getBattey_percentage());
                jGroup.put("battery_status", batteryModel.getCharging_status());
                jGroup.put("version_number", VERSION_NUMBER);
                jArray.put(jGroup);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jResult;
    }
    private void sendDataToServer(String accessToken, JSONArray jArray) {
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            UserToken access_token = realm.where(UserToken.class).findFirst();
         apiClient.SendAllCallLogData(jArray, "Bearer " + access_token.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            Toast.makeText(context, "Yours Session is Expired.Please Try after Login..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (response.body().getResult().equals("success")) {
                            editor.putString(UpdatedTimeRecord, String.valueOf(getTodayTimestamp()));
                            Log.d("newShared", String.valueOf(getTodayTimestamp()));
                            editor.commit();
                        }
                    }
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }
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

    public long getMidnightTimestamp(Date timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();


        //return timestamp - timestamp % 86400*1000; // 24 * 60 * 60 sec in one day
    }
}
