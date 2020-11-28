package com.example.my_weather_app.provider;

import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.BaseColumns;

public interface WeatherMeasurementsContract {
    Uri CONTENT_URI = Uri.parse("content://com.example.my_weather_app.provider/weather_measurements");
    /* Inner class that defines the table contents*/
    interface WeatherMeasurementsColumns extends BaseColumns {
        public static final String TABLE_NAME = "weather_measurements";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_AIR_TEMPERATURE = "air_temperature";
        public static final String COLUMN_AIR_HUMIDITY = "air_humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_AIR_QUALITY = "air_quality";
        public static final String COLUMN_GROUND_TEMPERATURE = "ground_temperature";
        public static final String COLUMN_RAINFALL = "rainfall";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_WIND_GUST = "wind_gusts";
        public static final String COLUMN_WIND_DIRECTION = "wind_direction";
    }

    static final int COL_NUM_DATETIME = 0;
    static final int COL_NUM_AIR_TEMPERATURE = 1;
    static final int COL_NUM_AIR_HUMIDITY = 2;
    static final int COL_NUM_PRESSURE = 3;
    static final int COL_NUM_AIR_QUALITY = 4;
    static final int COL_NUM_GROUND_TEMPERATURE = 5;
    static final int COL_NUM_RAINFALL = 6;
    static final int COL_NUM_WIND_SPEED = 7;
    static final int COL_NUM_WIND_DIRECTION = 8;
    static final int COL_NUM_WIND_GUST = 9;
}
