package com.milesforce.mwbewb.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.milesforce.mwbewb.Activities.LoginActivity;

public class BatterPercentage {
    IntentFilter intentfilter;
    int deviceStatus;
    String currentBatteryStatus = "Battery Info";

    public BatteryModel getBattertPercentage(Context context) {
        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(broadcastreceiver, intentfilter);
        BatteryModel batteryModel = new BatteryModel();
        batteryModel.setBattey_percentage(ConstantUtills.BATTERY_PERCENTAGE + " % ");
        batteryModel.setCharging_status(ConstantUtills.BATTERY_STATUS);

        //  context.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //  BatteryModel batteryModel = new BatteryModel();
        //  context.registerReceiver()


        return batteryModel;
    }

    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryLevel = (int) (((float) level / (float) scale) * 100.0f);
            ConstantUtills.BATTERY_PERCENTAGE = String.valueOf(batteryLevel);

            if (deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING) {

                ConstantUtills.BATTERY_STATUS = "Charging at " + batteryLevel + " %";

            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {

                ConstantUtills.BATTERY_STATUS = "Discharging at " + batteryLevel + " %";

            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL) {

                ConstantUtills.BATTERY_STATUS = "Battery Full at " + batteryLevel + " %";

            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {

                ConstantUtills.BATTERY_STATUS = "Unknown at " + batteryLevel + " %";
            }


            if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {

                ConstantUtills.BATTERY_STATUS = " Not Charging at " + batteryLevel + " %";

            }

        }
    };

}
