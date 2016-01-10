package com.yifanfwu.proximitylock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && preferences.getBoolean("enabled", false)) {
            Log.d("Boot completed: ", "success");
            Intent startIntent = new Intent(context, SensorService.class);
            context.startService(startIntent);
        }
    }
}
