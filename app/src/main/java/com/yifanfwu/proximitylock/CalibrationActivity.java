package com.yifanfwu.proximitylock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);

        Intent intent = new Intent(this, SensorService.class);
        stopService(intent);

        getSupportFragmentManager().beginTransaction().replace(R.id.calibrate_fragment, new InstructionActivity.CalibrateFragment()).commit();
    }
}
