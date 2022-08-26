package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;

public class TriggerCallWIthTelephoneManager {
    public static void TriggerCall(String mobilenumber, Context context) {
        TelecomManager telecomManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        }
        Uri uri = Uri.fromParts("tel", mobilenumber, null);
        Bundle extras = new Bundle();
        extras.putBoolean(TelecomManager.METADATA_IN_CALL_SERVICE_UI, false);
        extras.putBoolean(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, false);
        try {
            if (telecomManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    telecomManager.showInCallScreen(true);
                }
            }
        } catch (SecurityException incoming) {
            incoming.printStackTrace();
        }
        try {
            if (telecomManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    telecomManager.placeCall(uri, extras);
                }
            }
        } catch (SecurityException unlikely) {
            unlikely.printStackTrace();
        }
    }
}
