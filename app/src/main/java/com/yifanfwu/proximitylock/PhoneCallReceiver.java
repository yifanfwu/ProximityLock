package com.yifanfwu.proximitylock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Date;

public class PhoneCallReceiver extends CallReceiver {

    SharedPreferences preferences;

    @Override
    protected void onIncomingCallStarted(Context context, String number, Date start) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", false)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onOutgoingCallStarted(Context context, String number, Date start) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", false)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onIncomingCallEnded(Context context, String number, Date start, Date end) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", false)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onOutgoingCallEnded(Context context, String number, Date start, Date end) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", false)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onMissedCall(Context context, String number, Date start) {
    }

}
