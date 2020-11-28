package com.example.my_weather_app.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_weather_app.R;
import com.example.my_weather_app.adapter.WeatherListAdapter;
import com.example.my_weather_app.provider.WeatherMeasurementsContract;
import com.example.my_weather_app.ui.WeatherDetailsActivity;
import com.example.my_weather_app.util.Constants;

/**
 * This fragment displays all the weather measurements read from the database
 * The list is displayed by a {@link androidx.cardview.widget.CardView}
 */
public class WeatherListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, WeatherListAdapter.WeatherListAdapterOnClickHandler {

    /*
     * This ID will be used to identify the Loader responsible for loading our weather measurement. In
     * some cases, one Activity can deal with many Loaders. However, in our case, there is only one.
     * We will still use this ID to initialize the loader and create the loader for best practice.
     * Please note that 44 was chosen arbitrarily. You can use whatever number you like, so long as
     * it is unique and consistent.
     */
    private static final int ID_WEATHER_LOADER = 44;

    /* Initialization of the adapter, the recycler view and the layout manager */
    private WeatherListAdapter mWeatherListAdapter;
    private RecyclerView mWeatherReadingsRecyclerView;
    LinearLayoutManager mLayoutManager;

    /* Required an empty public constructor */
    public WeatherListFragment() {
    }

    ;

    /*
     * The columns of data that we are interested in displaying within our WeatherStationActivity's list of
     * weather data.
     */
    static final String[] WEATHER_MEASUREMENTS = {
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_DATETIME,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_TEMPERATURE,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_HUMIDITY,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_PRESSURE,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_AIR_QUALITY,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_GROUND_TEMPERATURE,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_RAINFALL,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_SPEED,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_DIRECTION,
            WeatherMeasurementsContract.WeatherMeasurementsColumns.COLUMN_WIND_GUST
    };

    /**
     * Inflates the RecyclerView
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_weather_list, container, false);

        /* Get a reference to the RecyclerView in the fragment_master_list xml layout file */
        mWeatherReadingsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_weather);

        /*
         * Use this setting to improve performance if you know that changes
         * in content do not change the layout size of the RecyclerView
         */
        mWeatherReadingsRecyclerView.setHasFixedSize(true);

        /* Use a linear layout manager */
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mWeatherReadingsRecyclerView.setLayoutManager(mLayoutManager);

        /*
         * The WeatherAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         *
         * Although passing in "this" twice may seem strange, it is actually a sign of separation
         * of concerns, which is best programming practice. The WeatherListAdapter requires an
         * Android Context (which all Activities are) as well as an onClickHandler. Since our
         * MainActivity implements the WeatherListAdapter WeatherOnClickHandler interface, "this"
         * is also an instance of that type of handler.
         */
        mWeatherListAdapter = new WeatherListAdapter(getContext(), this);

        /* Setting the adapter attaches it to the RecyclerView in our layout */
        mWeatherReadingsRecyclerView.setAdapter(mWeatherListAdapter);

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getActivity().getSupportLoaderManager().initLoader(ID_WEATHER_LOADER, null, this);

        return rootView;
    }

    /**
     * Called by the {@link LoaderManager} when a new Loader needs to be
     * created. This Activity only uses one loader, so we don't necessarily NEED to check the
     * loaderId, but this is certainly best practice.
     *
     * @param id   The loader ID for which we need to create a loader
     * @param args Any arguments supplied by the caller
     * @return A new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), WeatherMeasurementsContract.CONTENT_URI, WEATHER_MEASUREMENTS, null, null, null);
    }

    /**
     * Called when a Loader has finished loading its data.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mWeatherListAdapter.swapCursor(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mWeatherListAdapter.swapCursor(null);
    }

    /**
     * This method is for responding to clicks from our list
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
    @Override
    public void onClick(String datetime, String temperature, String humidity, String pressure, String airQuality,
                        String groundTemperature, String rainfall, String windSpeed, String windDirection, String windGust) {
        /* Handle the two-pane case and replace existing fragment */
        if (getActivity().findViewById(R.id.weather_details_container) != null) {

            /* Create two pane interaction */
            WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment(datetime, temperature, humidity, pressure, airQuality,
                    groundTemperature, rainfall, windSpeed, windDirection, windGust);

            /* Replace the old fragment with a new one */
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.weather_details_container, weatherDetailsFragment).commit();
        } else {

            /*
             * Handle the single-pane phone case by passing information in a Bundle attached to an Intent
             * Put the information in a Bundle and attach it to an Intent that will launch a WeatherDetailsActivity
             */
            Bundle b = new Bundle();
            b.putString(Constants.DATETIME_INTENT_EXTRA, datetime);
            b.putString(Constants.TEMPERATURE_INTENT_EXTRA, temperature);
            b.putString(Constants.HUMIDITY_INTENT_EXTRA, humidity);
            b.putString(Constants.PRESSURE_INTENT_EXTRA, pressure);
            b.putString(Constants.AIR_QUALITY_INTENT_EXTRA, airQuality);
            b.putString(Constants.GROUND_TEMPERATURE_INTENT_EXTRA, groundTemperature);
            b.putString(Constants.RAINFALL_INTENT_EXTRA, rainfall);
            b.putString(Constants.WIND_SPEED_INTENT_EXTRA, windSpeed);
            b.putString(Constants.WIND_DIRECTION_INTENT_EXTRA, windDirection);
            b.putString(Constants.WIND_GUST_INTENT_EXTRA, windGust);

            /* Attach the Bundle to an intent */
            final Intent intent = new Intent(getContext(), WeatherDetailsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
