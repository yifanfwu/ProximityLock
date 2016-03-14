package com.yifanfwu.proximitylock;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

import android.widget.ImageView;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class InstructionActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager viewPager;
    private CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        viewPager.setAdapter(sectionsPagerAdapter);
        indicator.setViewPager(viewPager);
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
            textView.setTextColor(getResources().getColor(R.color.white_text));
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
            textView.setTextColor(getResources().getColor(R.color.white_text));
            textView.setText(getResources().getString(R.string.point_blurb));
            return rootView;
        }
    }

    public static class LocateFragment extends Fragment { // needs its own layout file

        public LocateFragment() {
        }

        public static LocateFragment newInstance() {
            return new LocateFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.graphic);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            TextView textView = (TextView) rootView.findViewById(R.id.blurb);
            textView.setTextColor(getResources().getColor(R.color.white_text));
            textView.setText(getResources().getString(R.string.locate_blurb));
            return rootView;
        }
    }

    public static class CalibrateFragment extends Fragment { // needs its own layout file

        public CalibrateFragment() {
        }

        public static CalibrateFragment newInstance() {
            return new CalibrateFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.graphic);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            TextView textView = (TextView) rootView.findViewById(R.id.blurb);
            textView.setTextColor(getResources().getColor(R.color.white_text));
            textView.setText(getResources().getString(R.string.calibrate_blurb));
            return rootView;
        }
    }
}
