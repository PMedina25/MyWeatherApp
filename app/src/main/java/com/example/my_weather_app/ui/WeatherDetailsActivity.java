package com.example.my_weather_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.my_weather_app.R;
import com.example.my_weather_app.fragment.WeatherDetailsFragment;
import com.example.my_weather_app.util.Constants;
import com.firebase.ui.auth.AuthUI;

public class WeatherDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        /* Only create new fragments when there is no previously saved state */
        if (savedInstanceState == null) {

            /* Create a new WeatherDetailsFragment instance and display it using the FragmentManager */
            WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment(getIntent().getStringExtra(Constants.DATETIME_INTENT_EXTRA),
                    getIntent().getStringExtra(Constants.TEMPERATURE_INTENT_EXTRA), getIntent().getStringExtra(Constants.HUMIDITY_INTENT_EXTRA), getIntent().getStringExtra(Constants.PRESSURE_INTENT_EXTRA),
                    getIntent().getStringExtra(Constants.AIR_QUALITY_INTENT_EXTRA), getIntent().getStringExtra(Constants.GROUND_TEMPERATURE_INTENT_EXTRA), getIntent().getStringExtra(Constants.RAINFALL_INTENT_EXTRA),
                    getIntent().getStringExtra(Constants.WIND_SPEED_INTENT_EXTRA), getIntent().getStringExtra(Constants.WIND_DIRECTION_INTENT_EXTRA), getIntent().getStringExtra(Constants.WIND_GUST_INTENT_EXTRA));

            /* Use a FragmentManager and transaction to add the fragment to the screen */
            FragmentManager fragmentManager = getSupportFragmentManager();

            /* Fragment transactions */
            fragmentManager.beginTransaction().replace(R.id.weather_details_container, weatherDetailsFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                // sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}