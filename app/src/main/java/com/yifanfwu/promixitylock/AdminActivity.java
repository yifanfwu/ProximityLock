package com.yifanfwu.promixitylock;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AdminActivity extends DeviceAdminReceiver {

    public static class Controller extends Activity {

        DevicePolicyManager mDPM;
        ComponentName mDeviceAdminSample;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
            mDeviceAdminSample = new ComponentName(Controller.this, AdminActivity.class);

            boolean active = mDPM.isAdminActive(mDeviceAdminSample);
            if (!active) { // Without permission

                Intent adminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
                startActivityForResult(adminIntent,1);

                mDPM.lockNow();
            } else {
                mDPM.lockNow(); // lock screen directly
            }
        }
    }

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