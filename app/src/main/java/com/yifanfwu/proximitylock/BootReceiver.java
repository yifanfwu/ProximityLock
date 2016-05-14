package com.yifanfwu.proximitylock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && preferences.getBoolean(Strings.ENABLED_KEY, false)) {
            Intent startIntent = new Intent(context, SensorService.class);
            context.startService(startIntent);
        }
    }
}
