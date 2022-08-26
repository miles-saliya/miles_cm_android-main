package com.milesforce.mwbewb.Utils;

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

import com.milesforce.mwbewb.Activities.CallInfoActivity;
import com.milesforce.mwbewb.Activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.StateHandler.IS_INCOMMING_EVENT;
import static com.milesforce.mwbewb.Utils.StateHandler.IS_ONOUTGOING_CALL_EVENT;
import static com.milesforce.mwbewb.Utils.StateHandler.IS_OUTGOING_EVENT;
import static com.milesforce.mwbewb.Utils.StateHandler.IS_SUCCESS_CALL;

public class EventTriggers extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        int state;
        if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE) && (IS_INCOMMING_EVENT || IS_OUTGOING_EVENT)) {
            state = TelephonyManager.CALL_STATE_IDLE;
            IS_INCOMMING_EVENT = false;
            IS_OUTGOING_EVENT = false;
            IS_ONOUTGOING_CALL_EVENT = false;
            // onCallEndded(context);

        } else if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) && !IS_INCOMMING_EVENT) {
            state = TelephonyManager.CALL_STATE_RINGING;
            Log.d("CallStatues-action", "CALL_STATE_RINGING : " + state);
            String mobile = intent.getStringExtra("incoming_number");
            if (mobile != null) {
                //  onIncomingEvent(mobile);
                IS_INCOMMING_EVENT = true;
            }
        } else if (stateStr != null && stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && !IS_INCOMMING_EVENT) {
            state = TelephonyManager.CALL_STATE_OFFHOOK;
            Log.d("CallStatues-action", "EXTRA_STATE_OFFHOOK : " + state);
            String outgoingMobile = intent.getStringExtra("incoming_number");
            Log.d("CallStatues-action", "EXTRA_STATE_OFFHOOK : " + outgoingMobile);
            if (outgoingMobile != null) {
                //  onOutgoingEvent(outgoingMobile);
                IS_INCOMMING_EVENT = true;
            }
        }
        /* }*/
    }

   /* protected abstract void onCallRecievedCallReceived(Context context);

    protected abstract void onCallEndded(Context context);*/

    private void onOutgoingEvent(String mobile) {
        if (!IS_ONOUTGOING_CALL_EVENT) {
            IS_ONOUTGOING_CALL_EVENT = true;
            //   onCallRecievedCallReceived(context);
            Intent it = new Intent("intent.my.action");
            it.putExtra("engagement", "engagement");
            it.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getName()));
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(it);
        }

    }

    private void onIncomingEvent(String mobile) {
        if (!IS_ONOUTGOING_CALL_EVENT) {
            IS_ONOUTGOING_CALL_EVENT = true;
            //  onCallRecievedCallReceived(context);
            Intent it = new Intent("intent.my.action");
            it.putExtra("engagement", "engagement");
            it.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getName()));
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(it);
        }
    }


}
