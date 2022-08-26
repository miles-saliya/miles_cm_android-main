package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
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

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.UpdatedTimeRecord;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class SyncAllMyCallRecords {
    Context context;
    String AccessToken;
    ArrayList<CallLogModel> logModels;
    JSONArray jArray;
    JSONObject jResult = new JSONObject();
    ApiClient apiClient;
    Realm realm;
    UserToken userToken;
    public void SyncMyCallLog(Context context, String AccessToken) {
        this.context = context;
        apiClient = ApiUtills.getAPIService();
       // this.AccessToken = AccessToken;

        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
        }
        this.AccessToken = userToken.getAccessToken();
        FetchAllCallLogData();
    }

    private void FetchAllCallLogData() {

        long todayMidnightTimestamp = getMidnightTimestamp(new Date());
        String Mid_nightDate = String.valueOf(todayMidnightTimestamp);
        Log.d("shared", String.valueOf(Mid_nightDate) + "Mid");
        getCallLogs(Mid_nightDate);


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
        apiClient.SendAllCallLogData(jArray, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
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
                            Toast.makeText(context, "Call Log Data Updated Upto Date", Toast.LENGTH_SHORT).show();
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

    public long getMidnightTimestamp(Date timestamp) {
       /* Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();*/
        Calendar cal = Calendar.getInstance();
        Calendar xdate = (Calendar) cal.clone();
        xdate.set(Calendar.DAY_OF_MONTH, cal.getTime().getDate() - 1);

        long time_stamp = xdate.getTimeInMillis();
    /*
        Timestamp ts=new Timestamp(date.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(ts));
      *//*  System.out.println(" Current Time " + cal.getTime().toString());
        System.out.println(" X Time " + xdate.getTime().toString());*/
        return xdate.getTimeInMillis();


        //return timestamp - timestamp % 86400*1000; // 24 * 60 * 60 sec in one day
    }

}
