package com.yifanfwu.proximitylock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.settings_container, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private static Context context;

        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            context = ProximityApp.getAppContext();

            addPreferencesFromResource(R.xml.preferences);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case Strings.ENABLED_KEY:
                    if (sharedPreferences.getBoolean(key, false)) {
                        context.startService(new Intent(context, SensorService.class));
                    } else {
                        context.stopService(new Intent(context, SensorService.class));
                    }
                    break;
                case Strings.PERSISTENT_KEY:
                    if (sharedPreferences.getBoolean(Strings.ENABLED_KEY, false)) {
                        // call onStartCommand again
                        context.stopService(new Intent(context, SensorService.class));
                        context.startService(new Intent(context, SensorService.class));
                    }
            }
        }
    }
}
