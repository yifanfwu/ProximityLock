package com.yifanfwu.proximitylock;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DevicePolicyManager mDPM;
    ComponentName mDeviceAdminSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdminSample = new ComponentName(this, SensorAdminReceiver.class);

        boolean active = mDPM.isAdminActive(mDeviceAdminSample);
        if (!active) { // Without permission
            Intent adminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
            startActivityForResult(adminIntent, 1);
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Button button = (Button) findViewById(R.id.button);
        if (preferences.getBoolean("enabled", false)) {
            button.setText(getResources().getString(R.string.button_disable));
            startService(new Intent(getApplicationContext(), SensorService.class));
        } else {
            button.setText(getResources().getString(R.string.button_enable));
            stopService(new Intent(getApplicationContext(), SensorService.class));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean("enabled", false)) {
                    button.setText(getResources().getString(R.string.button_enable));
                    preferences.edit().putBoolean("enabled", false).apply();
                    stopService(new Intent(getApplicationContext(), SensorService.class));
                } else {
                    button.setText(getResources().getString(R.string.button_disable));
                    preferences.edit().putBoolean("enabled", true).apply();
                    startService(new Intent(getApplicationContext(), SensorService.class));
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Button button = (Button) findViewById(R.id.button);
        if (preferences.getBoolean("enabled", false)) {
            button.setText(getResources().getString(R.string.button_disable));
            startService(new Intent(getApplicationContext(), SensorService.class));
        } else {
            button.setText(getResources().getString(R.string.button_enable));
            stopService(new Intent(getApplicationContext(), SensorService.class));
        }

        if (preferences.getBoolean("persistent", true) && preferences.getBoolean("enabled", false)) {
            stopService(new Intent(getApplicationContext(), SensorService.class));
            startService(new Intent(getApplicationContext(), SensorService.class));
        } else if (!preferences.getBoolean("persistent", true) && preferences.getBoolean("enabled", false)) {
            stopService(new Intent(getApplicationContext(), SensorService.class));
            startService(new Intent(getApplicationContext(), SensorService.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
