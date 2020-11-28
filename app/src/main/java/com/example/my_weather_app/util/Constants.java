package com.example.my_weather_app.util;

public class Constants {

    /* Labels para la notificación de red */
    public static final String NETWORK_DISABLED_ALERT_TITLE = "Network is disabled";
    public static final String NETWORK_ALERT_MESSAGE = "Network needs to be enabled";
    public static final String NETWORK_ALERT_POSITIVE_BUTTON = "ENABLE WIFI";
    public static final String NETWORK_ALERT_NEGATIVE_BUTTON = "CANCEL";

    /* OpenWeatherMapActivity constants */
    public static final String JSON_LOG_TAG = "JSON ";
    public static final String EXCEPTION_LOG_TAG = "Excepción: ";
    public static final String TLS = "TLS";
    public static final String DATE_TIME_FORMAT = "EEE, d MMM yyyy HH:mm:ss";
    public static final Integer HTTP_OK = 200;
    public static final String IOSTREAM_READ_FAILURE = "Fallo al leer iostream";
    public static final String ENTITY_READ_FAILURE = "Fallo al leer entity";
    public static final String FAILURE_ERROR = "Fallo: Error ";
    public static final String INPUT_STREAM_LOG_TAG = "InputStream";
    public static final String DATETIME_JSON_KEY = "dt";
    public static final String WEATHER_JSON_KEY = "weather";
    public static final String MAIN_JSON_KEY = "main";
    public static final String TEMPERATURE_JSON_KEY = "temp";
    public static final String TEMPERATURE_MIN_JSON_KEY = "temp_min";
    public static final String TEMPERATURE_MAX_JSON_KEY = "temp_max";
    public static final String HUMIDITY_JSON_KEY = "humidity";
    public static final String PRESSURE_JSON_KEY = "pressure";
    public static final String WIND_JSON_KEY = "wind";
    public static final String SPEED_JSON_KEY = "speed";
    public static final String DEG_JSON_KEY = "deg";
    public static final String CONNECTION_ERROR = "Error en la conexión, inténtelo de nuevo";
    public static final String DEGREES = "º";
    public static final String PERCENTAGE = "%";
    public static final String HPA = " hPa";
    public static final String ICA = " ICA";
    public static final String MM = " mm";
    public static final String KM_H = " km/h";
    public static final long MILLISECONDS_MULTIPLIER = 1000L;

    /* Switch-Case Image Icons */
    public static final String THUNDERSTROM_CASE = "Thunderstorm";
    public static final String DRIZZLE_CASE = "Drizzle";
    public static final String RAIN_CASE = "Rain";
    public static final String SNOW_CASE = "Snow";
    public static final String MIST_CASE = "Mist";
    public static final String SMOKE_CASE = "Smoke";
    public static final String HAZE_CASE = "Haze";
    public static final String FOG_CASE = "Fog";
    public static final String SAND_CASE = "Sand";
    public static final String DUST_CASE = "Dust";
    public static final String ASH_CASE = "Ash";
    public static final String SQUALL_CASE = "Squall";
    public static final String TORNADO_CASE = "Tornado";
    public static final String CLEAR_CASE = "Clear";
    public static final String CLOUDS_CASE = "Clouds";

    /* WeatherStationActivity constants */
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    public static final String WEATHER_MEASUREMENTS_DATABASE_REFERENCE = "weather_measurements";
    public static final String SIGNED_IN_TOAST = "Signed in";
    public static final String SIGNED_IN_CANCELED_TOAST = "Signed in canceled";

    /* Constants related to information passed between activities */
    public static final String DATETIME_INTENT_EXTRA = "DateTime";
    public static final String TEMPERATURE_INTENT_EXTRA = "Temperature";
    public static final String HUMIDITY_INTENT_EXTRA = "Humidity";
    public static final String PRESSURE_INTENT_EXTRA = "Pressure";
    public static final String AIR_QUALITY_INTENT_EXTRA = "AirQuality";
    public static final String GROUND_TEMPERATURE_INTENT_EXTRA = "GroundTemperature";
    public static final String RAINFALL_INTENT_EXTRA = "Rainfall";
    public static final String WIND_SPEED_INTENT_EXTRA = "WindSpeed";
    public static final String WIND_DIRECTION_INTENT_EXTRA = "WindDirection";
    public static final String WIND_GUST_INTENT_EXTRA = "WindGust";

}
