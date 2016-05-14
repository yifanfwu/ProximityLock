package com.yifanfwu.proximitylock;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class SensorService extends Service implements SensorEventListener {

    private Sensor proximitySensor;
    private SensorManager sensorManager;
    private SharedPreferences preferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        final long start = System.currentTimeMillis();

        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);
        final int timeout = Integer.parseInt(preferences.getString(Strings.TIMEOUT_KEY, "300"));
        final float calibration = Float.parseFloat(preferences.getString(Strings.CALIBRATION_KEY, "123.4"));

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (event.values[0] == calibration && System.currentTimeMillis() - start < timeout) {
                    //do nothing
                }
                if (System.currentTimeMillis() - start >= timeout) {
                    DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
                    devicePolicyManager.lockNow();
                }
            }
        });
        t.start();
    }
    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.proximitySensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        this.sensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);
        boolean persistent = this.preferences.getBoolean(Strings.PERSISTENT_KEY, true);

        if (persistent) {
            Intent temp = new Intent(SensorService.this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(SensorService.this, 0, temp, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Proximity Lock")
                    .setContentText("Touch proximity sensor to lock")
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .setContentIntent(pIntent);
            Notification barNotif = builder.build();
            this.startForeground(1, barNotif);
        }
        return Service.START_STICKY;
    }
}
