package com.example.my_weather_app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_weather_app.R;
import com.example.my_weather_app.util.Constants;

/**
 * This fragment displays the weather measurements details extracted from
 * the {@link androidx.cardview.widget.CardView} item
 */
public class WeatherDetailsFragment extends Fragment {

    /* Declaration of all the views and String variables */
    private TextView mDatetimeView, mCurrentTemperatureView, mTemperatureView,
            mHumidityView, mPressureView, mAirQualityView, mGroundTemperatureView,
            mRainfallView, mWindSpeedView, mWindDirectionView, mWindGustView;
    private ImageView mEmojiView;
    private String datetime, temperature, humidity, pressure, airQuality, groundTemperature,
            rainfall, windSpeed, windDirection, windGust;

    /* Required an empty public constructor */
    public WeatherDetailsFragment() {
    }

    ;

    /**
     * Constructor with all the necessary parameters for the initialization
     * of the WeatherDetailsFragment Object
     *
     * @param datetime
     * @param temperature
     * @param humidity
     * @param pressure
     * @param airQuality
     * @param groundTemperature
     * @param rainfall
     * @param windSpeed
     * @param windDirection
     * @param windGust
     */
    public WeatherDetailsFragment(String datetime, String temperature, String humidity, String pressure,
                                  String airQuality, String groundTemperature, String rainfall,
                                  String windSpeed, String windDirection, String windGust) {
        this.datetime = datetime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.airQuality = airQuality;
        this.groundTemperature = groundTemperature;
        this.rainfall = rainfall;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windGust = windGust;
    }

    /**
     * Inflates the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Load the saved state if there is one */
        if (savedInstanceState != null) {
            datetime = savedInstanceState.getString(Constants.DATETIME_INTENT_EXTRA);
            temperature = savedInstanceState.getString(Constants.TEMPERATURE_INTENT_EXTRA);
            humidity = savedInstanceState.getString(Constants.HUMIDITY_INTENT_EXTRA);
            pressure = savedInstanceState.getString(Constants.PRESSURE_INTENT_EXTRA);
            airQuality = savedInstanceState.getString(Constants.AIR_QUALITY_INTENT_EXTRA);
            groundTemperature = savedInstanceState.getString(Constants.GROUND_TEMPERATURE_INTENT_EXTRA);
            rainfall = savedInstanceState.getString(Constants.RAINFALL_INTENT_EXTRA);
            windSpeed = savedInstanceState.getString(Constants.WIND_SPEED_INTENT_EXTRA);
            windDirection = savedInstanceState.getString(Constants.WIND_DIRECTION_INTENT_EXTRA);
            windGust = savedInstanceState.getString(Constants.WIND_GUST_INTENT_EXTRA);
        }

        /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_weather_details, container, false);

        /* Initialization of views*/
        mDatetimeView = (TextView) rootView.findViewById(R.id.date_time);
        mCurrentTemperatureView = (TextView) rootView.findViewById(R.id.current_temperature);
        mTemperatureView = (TextView) rootView.findViewById(R.id.temperature);
        mHumidityView = (TextView) rootView.findViewById(R.id.humidity);
        mPressureView = (TextView) rootView.findViewById(R.id.pressure);
        mAirQualityView = (TextView) rootView.findViewById(R.id.air_quality);
        mGroundTemperatureView = (TextView) rootView.findViewById(R.id.ground_temperature);
        mRainfallView = (TextView) rootView.findViewById(R.id.rainfall);
        mWindSpeedView = (TextView) rootView.findViewById(R.id.wind_speed);
        mWindDirectionView = (TextView) rootView.findViewById(R.id.wind_direction);
        mWindGustView = (TextView) rootView.findViewById(R.id.wind_gust);
        mEmojiView = (ImageView) rootView.findViewById(R.id.weather_icon);

        /* Set the value of the different TextViews */
        mDatetimeView.setText(datetime);
        mCurrentTemperatureView.setText((int) Double.parseDouble(temperature) + Constants.DEGREES);
        mTemperatureView.setText((int) Double.parseDouble(temperature) + Constants.DEGREES);
        mHumidityView.setText((int) Double.parseDouble(humidity) + Constants.PERCENTAGE);
        mPressureView.setText((int) Double.parseDouble(pressure) + Constants.HPA);
        mAirQualityView.setText((int) Double.parseDouble(airQuality) + Constants.ICA);
        mGroundTemperatureView.setText((int) Double.parseDouble(groundTemperature) + Constants.DEGREES);
        mRainfallView.setText((int) Double.parseDouble(rainfall) + Constants.MM);
        mWindSpeedView.setText((int) Double.parseDouble(windSpeed) + Constants.KM_H);
        mWindDirectionView.setText((int) Double.parseDouble(windDirection) + Constants.DEGREES);
        mWindGustView.setText((int) Double.parseDouble(windGust) + Constants.KM_H);

        /* Set the image view based on the temperature that has been read */
        if (Double.parseDouble(temperature) >= 30) {
            mEmojiView.setImageResource(R.drawable.sudor);
        } else if (Double.parseDouble(temperature) < 30 && Double.parseDouble(temperature) >= 15) {
            mEmojiView.setImageResource(R.drawable.emoji);
        } else if (Double.parseDouble(temperature) < 15 && Double.parseDouble(temperature) >= 0) {
            mEmojiView.setImageResource(R.drawable.frio);
        } else {
            mEmojiView.setImageResource(R.drawable.congelado);
        }
        return rootView;
    }

    /**
     * Override onSaveInstanceState and save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.DATETIME_INTENT_EXTRA, datetime);
        outState.putString(Constants.TEMPERATURE_INTENT_EXTRA, temperature);
        outState.putString(Constants.HUMIDITY_INTENT_EXTRA, humidity);
        outState.putString(Constants.PRESSURE_INTENT_EXTRA, pressure);
        outState.putString(Constants.AIR_QUALITY_INTENT_EXTRA, airQuality);
        outState.putString(Constants.GROUND_TEMPERATURE_INTENT_EXTRA, groundTemperature);
        outState.putString(Constants.RAINFALL_INTENT_EXTRA, rainfall);
        outState.putString(Constants.WIND_SPEED_INTENT_EXTRA, windSpeed);
        outState.putString(Constants.WIND_DIRECTION_INTENT_EXTRA, windDirection);
        outState.putString(Constants.WIND_GUST_INTENT_EXTRA, windGust);
    }
}