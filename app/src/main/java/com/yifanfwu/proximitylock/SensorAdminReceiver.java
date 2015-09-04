package com.yifanfwu.proximitylock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorAdminReceiver extends DeviceAdminReceiver {


    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.i("Device Admin: ", "Enabled");
    }

    @Override
    public String onDisableRequested(Context context, Intent intent) {
        return "Admin disable requested";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.i("Device Admin: ", "Disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        Log.i("Device Admin: ", "Password changed");
    }
}