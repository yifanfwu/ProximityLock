package com.yifanfwu.proximitylock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PhoneCallReceiver extends CallReceiver {

    private SharedPreferences preferences;

    @Override
    protected void onIncomingCallStarted(Context context) {
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onOutgoingCallStarted(Context context) {
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
        }
    }

    @Override
    protected void onIncomingCallEnded(Context context) {
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onOutgoingCallEnded(Context context) {
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onMissedCall(Context context) {
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, true)) {
            Intent intent = new Intent(context, SensorService.class);
            context.stopService(intent);
            context.startService(intent);
        }
    }

}
