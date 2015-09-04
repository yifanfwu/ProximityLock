package com.yifanfwu.promixitylock;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        } else {
            button.setText(getResources().getString(R.string.button_enable));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
