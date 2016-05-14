package com.yifanfwu.proximitylock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalibrationActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor proximitySensor;
    private SensorManager sensorManager;
    private TextView value;
    private float calibration;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);

        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);

        Intent intent = new Intent(this, SensorService.class);
        stopService(intent);

        this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.proximitySensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        this.sensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        this.value = (TextView) findViewById(R.id.value);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (this.preferences.getString(Strings.CALIBRATION_KEY, "123.0").equals("123.0")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        Button button = (Button) findViewById(R.id.calibrate_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString(Strings.CALIBRATION_KEY, Float.toString(calibration)).apply();
                Toast.makeText(getApplicationContext(), "Sensor calibrated: " + Float.toString(calibration), Toast.LENGTH_SHORT).show();

                TextView description = (TextView) findViewById(R.id.description);
                description.setText(getResources().getString(R.string.calibrate_back));
                description.setTextAppearance(getApplicationContext(), R.style.bold_text);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                value.setText("TEST ME");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibration, menu);
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        this.calibration = event.values[0];
        this.value.setText(Float.toString(calibration));

        if (!this.preferences.getString(Strings.CALIBRATION_KEY, "123.0").equals("123.0")) {
            if (this.preferences.getString(Strings.CALIBRATION_KEY, "123.0").equals(Float.toString(this.calibration))) {
                this.value.setText(getResources().getString(R.string.locking));
            } else {
                this.value.setText(getResources().getString(R.string.not_locking));
            }
        }
    }
}
