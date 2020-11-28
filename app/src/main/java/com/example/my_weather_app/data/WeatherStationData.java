package com.example.my_weather_app.data;

/**
 * Structure used for the weather data read
 * from Firebase Database, which comes from
 * the weather station
 */
public class WeatherStationData {

    private String datetime;
    private Double airTemperature;
    private Double airHumidity;
    private Double airPressure;
    private Double airQuality;
    private Double groundTemperature;
    private Double groundHumidity;
    private Double windSpeed;
    private Double windGustSpeed;
    private Double windDirection;
    private Double rainfall;

    public WeatherStationData() {

    }

    public WeatherStationData(String datetime, Double airTemperature, Double airHumidity, Double airPressure, Double airQuality,
                              Double groundTemperature, Double windSpeed, Double windGustSpeed, Double windDirection, Double rainfall) {
        this.datetime = datetime;
        this.airTemperature = airTemperature;
        this.airHumidity = airHumidity;
        this.airPressure = airPressure;
        this.airQuality = airQuality;
        this.groundTemperature = groundTemperature;
        this.windSpeed = windSpeed;
        this.windGustSpeed = windGustSpeed;
        this.windDirection = windDirection;
        this.rainfall = rainfall;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Double airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(Double airPressure) {
        this.airPressure = airPressure;
    }

    public Double getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(Double airQuality) {
        this.airQuality = airQuality;
    }

    public Double getGroundTemperature() {
        return groundTemperature;
    }

    public void setGroundTemperature(Double groundTemperature) {
        this.groundTemperature = groundTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindGustSpeed() {
        return windGustSpeed;
    }

    public void setWindGusts(Double windGustSpeed) {
        this.windGustSpeed = windGustSpeed;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Double windDirection) {
        this.windDirection = windDirection;
    }

    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(Double rainfall) {
        this.rainfall = rainfall;
    }

    @Override
    public String toString() {
        return "WeatherStationData{" +
                "datetime='" + datetime + '\'' +
                ", airTemperature=" + airTemperature +
                ", airHumidity=" + airHumidity +
                ", pressure=" + airPressure +
                ", airQuality=" + airQuality +
                ", groundTemperature=" + groundTemperature +
                ", windSpeed=" + windSpeed +
                ", windGustSpeed=" + windGustSpeed +
                ", windDirection=" + windDirection +
                ", rainfall=" + rainfall +
                '}';
    }
}
