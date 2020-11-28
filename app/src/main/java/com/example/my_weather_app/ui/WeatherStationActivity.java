package com.example.my_weather_app.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.my_weather_app.R;
import com.example.my_weather_app.data.WeatherStationData;
import com.example.my_weather_app.fragment.WeatherDetailsFragment;
import com.example.my_weather_app.provider.WeatherMeasurementsContract;
import com.example.my_weather_app.util.Constants;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class WeatherStationActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    private String mUsername;

    /* Firebase instance variables */
    private FirebaseDatabase mFireDatabase; // The entry point for the database
    private DatabaseReference mWeatherDataDatabaseReference; // Object that reference the messages portion of the database
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    /*
     * Variable to track whether to display a two-pane or single-pane UI
     * A single-pane display refers to phone screens, and two-pane to larger table screens
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station);

        mUsername = Constants.ANONYMOUS;

        /* Initialize the progress bar */
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        /* Initialize progress bar */
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        /* Initialize Firebase components */
        mFireDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        /* Get the database reference */
        mWeatherDataDatabaseReference = mFireDatabase.getReference().child(Constants.WEATHER_MEASUREMENTS_DATABASE_REFERENCE);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    /* User is signed in */
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    /* User is signed out */
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            Constants.RC_SIGN_IN);
                }
            }
        };

        /* If your are making a two-pane display, add new WeatherDetailsFragment */
        if (findViewById(R.id.weather_details_container) != null && savedInstanceState == null) {

            /* This LinearLayout will only initially exist in the two-pane tablet case */
            mTwoPane = true;

            /* Create a new WeatherDetailsFragment and display it */
            WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment("", String.valueOf(0), String.valueOf(0),
                    String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0),
                    String.valueOf(0), String.valueOf(0), String.valueOf(0));


            /* Use a FragmentManager and transaction to add the fragment to the screen */
            FragmentManager fragmentManager = getSupportFragmentManager();

            /* Fragment transactions */
            fragmentManager.beginTransaction().add(R.id.weather_details_container, weatherDetailsFragment).commit();

        } else {
            /* We are in single-pane mode and displaying fragments on a phone in separate activities */
            mTwoPane = false;
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                /* Sign in succeeded, set up the UI */
                if (mTwoPane) {
                    Snackbar.make(findViewById(R.id.contenedorHorizontal), Constants.SIGNED_IN_TOAST, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(R.id.weather_station_layout), Constants.SIGNED_IN_TOAST, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                /* Sign in was canceled by the user, finish the activity */
                Snackbar.make(findViewById(R.id.weather_station_layout), Constants.SIGNED_IN_CANCELED_TOAST, Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = Constants.ANONYMOUS;
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        cleanTable();
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot /* Contains the message that has been added */, @Nullable String previousChildName) {
                    WeatherStationData weatherStationData = snapshot.getValue(WeatherStationData.class);
                    storeValues(weatherStationData.getDatetime(), weatherStationData.getAirTemperature(), weatherStationData.getAirHumidity(), weatherStationData.getAirPressure(), weatherStationData.getAirQuality(),
                            weatherStationData.getGroundTemperature(), weatherStationData.getWindSpeed(), weatherStationData.getWindGustSpeed(),
                            weatherStationData.getWindDirection(), weatherStationData.getRainfall());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            mWeatherDataDatabaseReference.addChildEventListener(mChildEventListener); // The reference defines what exactly I'm listening
        }
    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mWeatherDataDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    /**
     * Insert the values read from Firebase Database into the SQLite Database
     *
     * @param dateTime
     * @param airTemperature
     * @param airHumidity
     * @param airPressure
     * @param airQuality
     * @param groundTemperature
     * @param windSpeed
     * @param windGustSpeed
     * @param windDirection
     * @param rainfall
     */
    private void storeValues(String dateTime, Double airTemperature, Double airHumidity, Double airPressure, Double airQuality, Double groundTemperature,
                             Double windSpeed, Double windGustSpeed, Double windDirection, Double rainfall) {
        // Gets the data repository in write mode
        ContentValues newRow = new ContentValues();

        // We assign the values for each row
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_DATETIME, dateTime);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_TEMPERATURE, airTemperature);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_HUMIDITY, airHumidity);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_PRESSURE, airPressure);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_QUALITY, airQuality);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_GROUND_TEMPERATURE, groundTemperature);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_SPEED, windSpeed);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_GUST, windGustSpeed);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_DIRECTION, windDirection);
        newRow.put(WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_RAINFALL, rainfall);

        getContentResolver().insert(WeatherMeasurementsContract.CONTENT_URI, newRow);
    }

    /**
     * Delete all the data from the SQLite database
     */
    private void cleanTable() {
        // Gets the data repository in write mode
        ContentValues rows = new ContentValues();

        getContentResolver().delete(WeatherMeasurementsContract.CONTENT_URI, null, null);
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