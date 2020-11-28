package com.example.my_weather_app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_weather_app.R;
import com.example.my_weather_app.provider.WeatherMeasurementsContract;
import com.example.my_weather_app.util.Constants;

/**
 * {@link WeatherListAdapter} exposes a list of weather measurements
 * from a {@link Cursor} to a {@link RecyclerView}.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherListAdapterViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    /*
     * Below, we've defined an interface to handle clicks on items within this Adapter. In the
     * constructor of our MasterListAdapter, we receive an instance of a class that has implemented
     * said interface. We store that instance in this variable to call the onClick method whenever
     * an item is clicked in the list.
     */
    final private WeatherListAdapterOnClickHandler mClickHandler;

    private Cursor mCursor;

    /**
     * The interface that receives onClick messages
     */
    public interface WeatherListAdapterOnClickHandler {
        void onClick(String datetime, String temperature, String humidity, String pressure,
                     String airQuality, String groundTemperature, String rainfall,
                     String windSpeed, String windDirection, String windGust);
    }

    /**
     * Creates a WeatherListAdapter.
     *
     * @param context      Used to talk to the UI and app resources
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public WeatherListAdapter(Context context, WeatherListAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a weather item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class WeatherListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        final ImageView mWeatherImage;
        final TextView mDateView;
        final TextView mTemperatureView;
        final TextView mHumidityView;


        WeatherListAdapterViewHolder(View view) {
            super(view);

            mWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            mDateView = (TextView) view.findViewById(R.id.dateView);
            mTemperatureView = (TextView) view.findViewById(R.id.temperatureTextView);
            mHumidityView = (TextView) view.findViewById(R.id.humidityTextView);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String datetime = mCursor.getString(WeatherMeasurementsContract.COL_NUM_DATETIME);
            String temperature = mCursor.getString(WeatherMeasurementsContract.COL_NUM_AIR_TEMPERATURE);
            String humidity = mCursor.getString(WeatherMeasurementsContract.COL_NUM_AIR_HUMIDITY);
            String pressure = mCursor.getString(WeatherMeasurementsContract.COL_NUM_PRESSURE);
            String airQuality = mCursor.getString(WeatherMeasurementsContract.COL_NUM_AIR_QUALITY);
            String groundTemperature = mCursor.getString(WeatherMeasurementsContract.COL_NUM_GROUND_TEMPERATURE);
            String rainfall = mCursor.getString(WeatherMeasurementsContract.COL_NUM_RAINFALL);
            String windSpeed = mCursor.getString(WeatherMeasurementsContract.COL_NUM_WIND_SPEED);
            String windDirection = mCursor.getString(WeatherMeasurementsContract.COL_NUM_WIND_DIRECTION);
            String windGust = mCursor.getString(WeatherMeasurementsContract.COL_NUM_WIND_GUST);


            mClickHandler.onClick(datetime, temperature, humidity, pressure, airQuality,
                    groundTemperature, rainfall, windSpeed, windDirection, windGust);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new MasterListAdapterViewHolder that holds the View for each list item
     */
    @Override
    public WeatherListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_card;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);


        return new WeatherListAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull WeatherListAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String dateTime = mCursor.getString(WeatherMeasurementsContract.COL_NUM_DATETIME);
        String airTemperature = mCursor.getString(WeatherMeasurementsContract.COL_NUM_AIR_TEMPERATURE);
        String airHumidity = mCursor.getString(WeatherMeasurementsContract.COL_NUM_AIR_HUMIDITY);

        holder.mDateView.setText(dateTime);
        holder.mTemperatureView.setText((int)Double.parseDouble(airTemperature) + Constants.DEGREES);
        holder.mHumidityView.setText((int)Double.parseDouble(airHumidity) + Constants.PERCENTAGE);

        if (Double.parseDouble(airTemperature) >= 30) {
            holder.mWeatherImage.setImageResource(R.drawable.sudor);
        }
        else if (Double.parseDouble(airTemperature) < 30 && Double.parseDouble(airTemperature) >= 15) {
            holder.mWeatherImage.setImageResource(R.drawable.emoji);
        }
        else if (Double.parseDouble(airTemperature) < 15 && Double.parseDouble(airTemperature) >= 0) {
            holder.mWeatherImage.setImageResource(R.drawable.frio);
        }
        else {
            holder.mWeatherImage.setImageResource(R.drawable.congelado);
        }
    }


    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our weather
     */
    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    /**
     * Swaps the cursor used by the WeatherListAdapter for its weather data. This method is called by
     * WeatherStationActivity after a load has finished, as well as when the Loader responsible for loading
     * the weather data is reset. When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newCursor the new cursor to use as WeatherListAdatper's data source
     */
    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
