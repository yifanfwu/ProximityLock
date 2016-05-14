package com.yifanfwu.proximitylock;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName deviceAdminSample;
    private SharedPreferences preferences;
    private Button enableButton;
    private Button calibrateButton;
    private ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.deviceAdminSample = new ComponentName(this, SensorAdminReceiver.class);

        boolean active = this.devicePolicyManager.isAdminActive(this.deviceAdminSample);
        if (!active) { // Without permission
            Intent adminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample);
            startActivityForResult(adminIntent, 1);
        }

        PreferenceManager.setDefaultValues(ProximityApp.getAppContext(), Strings.SHARED_PREF_NAME, MODE_PRIVATE, R.xml.preferences, false);
        PreferenceManager.setDefaultValues(ProximityApp.getAppContext(), Strings.SHARED_PREF_NAME, MODE_PRIVATE, R.xml.welcome_preference, false);
        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);

        if (this.preferences.getBoolean(Strings.WELCOME_KEY, true)) {
            this.preferences.edit().putBoolean(Strings.WELCOME_KEY, false).apply();
            Intent intent = new Intent (this, CalibrationActivity.class);
            startActivity(intent);
        }

        this.settingsButton = (ImageView) findViewById(R.id.settings_icon);
        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        this.enableButton = (Button) findViewById(R.id.enable);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, false)) {
            this.enableButton.setText(getResources().getString(R.string.button_disable));
            startService(new Intent(getApplicationContext(), SensorService.class));
        } else {
            this.enableButton.setText(getResources().getString(R.string.button_enable));
            stopService(new Intent(getApplicationContext(), SensorService.class));
        }

        this.enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean(Strings.ENABLED_KEY, false)) {
                    enableButton.setText(getResources().getString(R.string.button_enable));
                    preferences.edit().putBoolean(Strings.ENABLED_KEY, false).apply();
                    stopService(new Intent(getApplicationContext(), SensorService.class));
                } else {
                    enableButton.setText(getResources().getString(R.string.button_disable));
                    preferences.edit().putBoolean(Strings.ENABLED_KEY, true).apply();
                    startService(new Intent(getApplicationContext(), SensorService.class));
                }
            }
        });

        this.calibrateButton = (Button) findViewById(R.id.calibrate);
        this.calibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalibrationActivity.class);
                startActivity(intent);
            }
        });

        Button test = (Button) findViewById(R.id.test_button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Button button = (Button) findViewById(R.id.enable);
        if (this.preferences.getBoolean(Strings.ENABLED_KEY, false)) {
            button.setText(getResources().getString(R.string.button_disable));
            startService(new Intent(getApplicationContext(), SensorService.class));
        } else {
            button.setText(getResources().getString(R.string.button_enable));
            stopService(new Intent(getApplicationContext(), SensorService.class));
        }

//        if (this.preferences.getBoolean(Strings.PERSISTENT_KEY, true) && this.preferences.getBoolean(Strings.ENABLED_KEY, false)) {
//            stopService(new Intent(getApplicationContext(), SensorService.class));
//            startService(new Intent(getApplicationContext(), SensorService.class));
//        } else if (!this.preferences.getBoolean(Strings.PERSISTENT_KEY, true) && this.preferences.getBoolean(Strings.ENABLED_KEY, false)) {
//            stopService(new Intent(getApplicationContext(), SensorService.class));
//            startService(new Intent(getApplicationContext(), SensorService.class));
//        }
    }
}
