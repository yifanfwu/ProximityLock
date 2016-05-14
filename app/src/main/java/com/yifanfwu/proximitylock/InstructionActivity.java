package com.yifanfwu.proximitylock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.relex.circleindicator.CircleIndicator;

public class InstructionActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager viewPager;
    private CircleIndicator indicator;
    static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        this.sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        this.viewPager = (ViewPager) findViewById(R.id.settings_container);
        this.indicator = (CircleIndicator) findViewById(R.id.indicator);

        this.viewPager.setAdapter(this.sectionsPagerAdapter);

        this.indicator.setViewPager(this.viewPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instruction, menu);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return IntroFragment.newInstance();
                case 1:
                    return PointFragment.newInstance();
                case 2:
                    return LocateFragment.newInstance();
                case 3:
                    return CalibrateFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class IntroFragment extends Fragment {

        public IntroFragment() {
        }

        public static IntroFragment newInstance() {
            return new IntroFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.graphic);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            TextView textView = (TextView) rootView.findViewById(R.id.blurb);
            textView.setText(getResources().getString(R.string.intro_blurb));
            return rootView;
        }
    }

    public static class PointFragment extends Fragment {

        public PointFragment() {
        }

        public static PointFragment newInstance() {
            return new PointFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.graphic);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            TextView textView = (TextView) rootView.findViewById(R.id.blurb);
            textView.setText(getResources().getString(R.string.point_blurb));
            return rootView;
        }
    }

    public static class LocateFragment extends Fragment implements SensorEventListener { // needs its own layout file

        TextView sensorValue;
        Sensor proximitySensor;
        SensorManager sensorManager;

        public LocateFragment() {
        }

        public static LocateFragment newInstance() {
            return new LocateFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_locate, container, false);
            this.sensorValue = (TextView) rootView.findViewById(R.id.sensor_value);
            this.sensorValue.setTextColor(getResources().getColor(R.color.white_text));

            TextView blurb = (TextView) rootView.findViewById(R.id.blurb);
            blurb.setText(getResources().getString(R.string.locate_blurb));

            this.sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            this.proximitySensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            this.sensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);

            return rootView;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            this.sensorValue.setText(Float.toString(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onPause() {
            super.onPause();
            this.sensorManager.unregisterListener(this);
        }
    }

    public static class CalibrateFragment extends Fragment implements SensorEventListener { // needs its own layout file

        TextView sensorValue;
        Sensor proximitySensor;
        SensorManager sensorManager;
        float calibration;
        SharedPreferences preferences;
        Button calibrateButton;

        public CalibrateFragment() {
        }

        public static CalibrateFragment newInstance() {
            return new CalibrateFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);

            View rootView = inflater.inflate(R.layout.fragment_locate, container, false);
            this.sensorValue = (TextView) rootView.findViewById(R.id.sensor_value);
            this.sensorValue.setTextColor(getResources().getColor(R.color.white_text));

            TextView blurb = (TextView) rootView.findViewById(R.id.blurb);
            blurb.setText(getResources().getString(R.string.calibrate_blurb));

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });

            this.calibrateButton = (Button) rootView.findViewById(R.id.calibrate_button);
            this.calibrateButton.setVisibility(View.VISIBLE);
            this.calibrateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preferences.edit().putString(Strings.CALIBRATION_KEY, Float.toString(calibration)).apply();
                    Toast.makeText(getActivity(), "Sensor calibrated!", Toast.LENGTH_SHORT).show();
                    sensorValue.setText(getResources().getString(R.string.test_me));

                    if (fab.getVisibility() == View.GONE) {
                        fab.setAlpha(0f);
                        fab.setVisibility(View.VISIBLE);
                        fab.animate().alpha(1f).setDuration(1000L);
                    }
                }
            });


            this.sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            this.proximitySensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            this.sensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);

            return rootView;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            this.calibration = event.values[0];
            this.sensorValue.setText(Float.toString(calibration));

            if (!this.preferences.getString(Strings.CALIBRATION_KEY, "123.4").equals("123.4")) {
                if (this.preferences.getString(Strings.CALIBRATION_KEY, "123.4").equals(Float.toString(calibration))) {
                    this.sensorValue.setText(getResources().getString(R.string.locking));
                } else {
                    this.sensorValue.setText(getResources().getString(R.string.not_locking));
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
