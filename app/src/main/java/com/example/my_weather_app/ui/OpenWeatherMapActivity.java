package com.example.my_weather_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_weather_app.R;
import com.example.my_weather_app.data.CurrentWeatherMeasurement;
import com.example.my_weather_app.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class OpenWeatherMapActivity extends AppCompatActivity {

    /* Views declaration */
    private TextView mDateTime, mWeatherDescription, mCurrentTemperature,
            mTemperatureMax, mTemperatureMin, mHumidity,
            mPressure, mWindSpeed, mWindDirection;
    private ImageView mWeatherIcon;

    /* Declaration of the CurrentWeatherMeasurement list */
    List<CurrentWeatherMeasurement> currentWeatherMeasurements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather_map);

        /* Views initialization */
        mDateTime = (TextView) findViewById(R.id.date_time);
        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        mWeatherDescription = (TextView) findViewById(R.id.weather_description);
        mCurrentTemperature = (TextView) findViewById(R.id.current_temperature);
        mTemperatureMax = (TextView) findViewById(R.id.temperature_max);
        mTemperatureMin = (TextView) findViewById(R.id.temperature_min);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mPressure = (TextView) findViewById(R.id.pressure);
        mWindSpeed = (TextView) findViewById(R.id.wind_speed);
        mWindDirection = (TextView) findViewById(R.id.wind_direction);
        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);

        /* URL from wich we obtain the data in JSON format */
        final String url = "https://api.openweathermap.org/data/2.5/weather?q=Osuna,es&appid=3f84badf430b1fdfaf2668a3840a046b";

        /* Json loader */
        CargarJsonTask task = new CargarJsonTask();
        task.execute(url);
    }

    /**
     * Asynchronous task that loads a Json in the background
     */
    private class CargarJsonTask extends AsyncTask<String, Integer, Boolean> {

        JSONObject jsonResponse;

        protected Boolean doInBackground(String... params) {
            boolean resul = false;
            String StringResponse;
            try {
                StringResponse = get(params[0]);
                jsonResponse = new JSONObject(StringResponse);
                resul = true;
            } catch (JSONException e) {
                Log.e(Constants.JSON_LOG_TAG, e.getLocalizedMessage());
            }
            return resul;
        }

        private String get(String url) {
            InputStream inputStream = null;
            String result = "";
            try {
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });
                SSLContext context = SSLContext.getInstance(Constants.TLS);
                context.init(null, new X509TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }}, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(
                        context.getSocketFactory());
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
                if (httpResponse.getStatusLine().getStatusCode() == Constants.HTTP_OK) {
                    HttpEntity entity = httpResponse.getEntity();
                    // receive response as inputStream
                    if (entity != null) {
                        inputStream = httpResponse.getEntity().getContent();
                        // convert inputstream to string
                        if (inputStream != null) {
                            result = convertInputStreamToString(inputStream);
                            System.out.println(result);
                        } else {
                            result = Constants.IOSTREAM_READ_FAILURE;
                        }
                    } else
                        result = Constants.ENTITY_READ_FAILURE;
                } else
                    result = Constants.FAILURE_ERROR + httpResponse.getStatusLine().getStatusCode();
            } catch (Exception e) {
                Log.d(Constants.INPUT_STREAM_LOG_TAG, e.getLocalizedMessage());
            }
            return result;
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(Boolean result) {
            try {
                CurrentWeatherMeasurement currentWeather = new CurrentWeatherMeasurement();
                JSONObject weather = new JSONObject(jsonResponse.toString());
                if (weather.has(Constants.DATETIME_JSON_KEY)) {
                    currentWeather.setDatetime(weather.getLong(Constants.DATETIME_JSON_KEY));
                }
                if (weather.has(Constants.WEATHER_JSON_KEY)) {
                    currentWeather.setDescription(new JSONArray(weather.getString(Constants.WEATHER_JSON_KEY)).getJSONObject(0).getString(Constants.MAIN_JSON_KEY).toString());
                }
                if (weather.has(Constants.MAIN_JSON_KEY)) {
                    currentWeather.setTemperature(kelvinToCelsiusConverter(new JSONObject(weather.getString(Constants.MAIN_JSON_KEY)).getDouble(Constants.TEMPERATURE_JSON_KEY)));
                }
                if (weather.has(Constants.MAIN_JSON_KEY)) {
                    currentWeather.setMaxTemperature(kelvinToCelsiusConverter(new JSONObject(weather.getString(Constants.MAIN_JSON_KEY)).getDouble(Constants.TEMPERATURE_MAX_JSON_KEY)));
                }
                if (weather.has(Constants.MAIN_JSON_KEY)) {
                    currentWeather.setMinTemperature(kelvinToCelsiusConverter(new JSONObject(weather.getString(Constants.MAIN_JSON_KEY)).getDouble(Constants.TEMPERATURE_MIN_JSON_KEY)));
                }
                if (weather.has(Constants.MAIN_JSON_KEY)) {
                    currentWeather.setHumidity(new JSONObject(weather.getString(Constants.MAIN_JSON_KEY)).getInt(Constants.HUMIDITY_JSON_KEY));
                }
                if (weather.has(Constants.MAIN_JSON_KEY)) {
                    currentWeather.setPressure(new JSONObject(weather.getString(Constants.MAIN_JSON_KEY)).getInt(Constants.PRESSURE_JSON_KEY));
                }
                if (weather.has(Constants.WIND_JSON_KEY)) {
                    currentWeather.setWindSpeed(new JSONObject(weather.getString(Constants.WIND_JSON_KEY)).getDouble(Constants.SPEED_JSON_KEY));
                }
                if (weather.has(Constants.WIND_JSON_KEY)) {
                    currentWeather.setWindDirection(new JSONObject(weather.getString(Constants.WIND_JSON_KEY)).getInt(Constants.DEG_JSON_KEY));
                }

                mDateTime.setText(String.valueOf(getDate(currentWeather.getDatetime())));
                mWeatherDescription.setText(currentWeather.getDescription());
                mCurrentTemperature.setText(currentWeather.getTemperature() + Constants.DEGREES);
                mTemperatureMax.setText(currentWeather.getMaxTemperature() + Constants.DEGREES);
                mTemperatureMin.setText(currentWeather.getMinTemperature() + Constants.DEGREES);
                mHumidity.setText(currentWeather.getHumidity() + Constants.PERCENTAGE);
                mPressure.setText(currentWeather.getPressure() + Constants.HPA);
                mWindSpeed.setText(currentWeather.getWindSpeed() + Constants.KM_H);
                mWindDirection.setText(currentWeather.getWindDirection() + Constants.DEGREES);

                setImageIcon(currentWeather.getDescription());

            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.open_weather_map_layout), Constants.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
                Log.e(Constants.EXCEPTION_LOG_TAG, e.toString());
            }
        }
    }

    /**
     * Function which converts fahrenheit to celsius.
     * @param kelvinValue
     * @return the value in celsius
     */
    private int kelvinToCelsiusConverter(Double kelvinValue) {
        return (int) (kelvinValue - 273);
    }

    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @return String representing date in specified format
     */
    private static String getDate(long milliSeconds)
    {
        /* Create a DateFormatter object for displaying date in specified format */
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);

        return formatter.format(new Date(milliSeconds*Constants.MILLISECONDS_MULTIPLIER));
    }

    /**
     * Set the image icon which depends on the
     * weather state that has been read.
     * @param description It corresponds to the weather state
     */
    private void setImageIcon(String description) {
        switch (description) {
            case Constants.THUNDERSTROM_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_storm);
                break;
            case Constants.DRIZZLE_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case Constants.RAIN_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_rain);
                break;
            case Constants.SNOW_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_snow);
                break;
            case Constants.MIST_CASE:
            case Constants.SMOKE_CASE:
            case Constants.HAZE_CASE:
            case Constants.FOG_CASE:
            case Constants.SAND_CASE:
            case Constants.DUST_CASE:
            case Constants.ASH_CASE:
            case Constants.SQUALL_CASE:
            case Constants.TORNADO_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_fog);
                break;
            case Constants.CLEAR_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_clear);
                break;
            case Constants.CLOUDS_CASE:
                mWeatherIcon.setImageResource(R.drawable.art_clouds);
                break;
        }
    }
}