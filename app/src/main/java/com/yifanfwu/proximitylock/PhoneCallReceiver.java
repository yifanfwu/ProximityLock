package com.yifanfwu.proximitylock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class PhoneCallReceiver extends CallReceiver {

    SharedPreferences preferences;

    @Override
    protected void onIncomingCallStarted(Context context) {
        Log.d("incoming", "started");
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onOutgoingCallStarted(Context context) {
        Log.d("outgoing", "started");
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onIncomingCallEnded(Context context) {
        Log.d("incoming", "ended");
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onOutgoingCallEnded(Context context) {
        Log.d("outgoing", "ended");
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onMissedCall(Context context) {
        Log.d("missed call", "??");
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("enabled", true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
            context.startService(intent);
        }
    }

}
