package com.example.my_weather_app.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherMeasurementsSQLiteHelper extends SQLiteOpenHelper {

    // SQL statement to create the table weather_readings
    String sqlCreate = "CREATE TABLE weather_measurements " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " datetime TEXT, " +
            " air_temperature REAL, " +
            " air_humidity REAL, " +
            " pressure REAL, " +
            " air_quality REAL, " +
            " ground_temperature REAL, " +
            " rainfall REAL, " +
            " wind_speed REAL, " +
            " wind_direction REAL, " +
            " wind_gusts REAL)";

    public WeatherMeasurementsSQLiteHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS weather_measurements");
        // The new version of the table is created
        db.execSQL(sqlCreate);
    }
}
