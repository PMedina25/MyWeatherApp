package com.example.my_weather_app.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WeatherMeasurementsProvider extends ContentProvider {

    private WeatherMeasurementsSQLiteHelper bd;
    private static final int WEATHER_MEASUREMENTS = 1;
    private static final int WEATHER_MEASUREMENTS_ID = 2;
    private static UriMatcher uriMatcher = null;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.my_weather_app.provider", "weather_measurements", WEATHER_MEASUREMENTS);
        uriMatcher.addURI("com.example.my_weather_app.provider", "weather_measurements/#", WEATHER_MEASUREMENTS_ID);
    }
    @Override
    public boolean onCreate() {
        bd = new WeatherMeasurementsSQLiteHelper(getContext(), "BDWeatherMeasurements", null, 1);
        return true;
    }

    /**
     * Handles query requests from clients. We will use this method in the Weather App to query for all
     * of our weather data.
     *
     * @param uri           The URI to query
     * @param projection    The list of columns to put into the cursor. If null, all columns are
     *                      included.
     * @param selection     A selection criteria to apply when filtering rows. If null, then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the
     *                      selection.
     * @param sortOrder     How the rows in the cursor should be sorted.
     * @return A Cursor containing the results of the query. In our implementation,
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String where = selection;
        if(uriMatcher.match(uri) == WEATHER_MEASUREMENTS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = bd.getReadableDatabase();
        return db.query("weather_measurements", projection, where, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match)
        {
            case WEATHER_MEASUREMENTS:
                return "vnd.android.cursor.dir/weather_measurements";
            case WEATHER_MEASUREMENTS_ID:
                return "vnd.android.cursor.item/weather_measurements";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);

        SQLiteDatabase sqlDB = bd.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case WEATHER_MEASUREMENTS:
                id = sqlDB.insert("weather_measurements", null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse("weather_measurements" + "/" + id);
    }

    /**
     * Handles requests to insert a set of new rows. I
     *
     * @param uri    The content:// URI of the insertion request.
     * @param contentValues An array of sets of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     *
     * @return The number of values that were inserted.
     */
    @Override
    public int bulkInsert(@NonNull Uri uri, @Nullable ContentValues[] contentValues) {
        int uriType = uriMatcher.match(uri);

        SQLiteDatabase sqlDB = bd.getWritableDatabase();

        long id = 0;
        switch (uriType) {

            // Only perform our implementation of bulkInsert if the URI matches the WEATHER_MEASUREMENTS code
            case WEATHER_MEASUREMENTS:
                sqlDB.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : contentValues) {
                        id = sqlDB.insert("weather_measurements", null, value);

                        if (id != -1) {
                            rowsInserted++;
                        }
                    }
                    sqlDB.setTransactionSuccessful();
                } finally {
                    sqlDB.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                // Return the number of rows inserted from our implementation of bulkInsert
                return rowsInserted;

            // If the URI does match WEATHER_MEASUREMENTS, return the super implementation of bulkInsert
            default:
                return super.bulkInsert(uri, contentValues);
        }
    }

    /**
     * Deletes data at a given URI with optional arguments for more fine tuned deletions.
     *
     * @param uri           The full URI to query
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs Used in conjunction with the selection statement
     * @return The number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        /* Users of the delete method will expect the number of rows deleted to be returned. */
        int numRowsDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (null == selection) selection = "1";

        switch (uriMatcher.match(uri)) {
            case WEATHER_MEASUREMENTS:
                numRowsDeleted = bd.getWritableDatabase().delete(
                        "weather_measurements",
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
