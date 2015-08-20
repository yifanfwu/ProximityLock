package com.yifanfwu.promixitylock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.logging.Handler;

public class SensorService extends Service implements SensorEventListener {

    Sensor proximitySensor;
    Sensor proxSensor;
    SensorManager sensorManager;
    SensorManager sm;

    TextView textView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("SensorService", "Hello");
//        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        final long start = System.currentTimeMillis();

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (event.values[0] < 3.0) {
                    if (System.currentTimeMillis() - start > 275) {
                        DevicePolicyManager mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
                        mDPM.lockNow();
                    }
                }
            }
        });
        t.start();
    }
    @Override
    public void onDestroy() {
//        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent temp = new Intent(SensorService.this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(SensorService.this, 0, temp, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Proximity Lock")
                .setContentText("Touch proximity sensor to lock")
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(pIntent);
        Notification barNotif = builder.build();
        this.startForeground(1, barNotif);

        return Service.START_STICKY;
    }
}
