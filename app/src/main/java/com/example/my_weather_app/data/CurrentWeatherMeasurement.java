package com.example.my_weather_app.data;

/**
 * Structure that handles the weather data read from the Open Weather Map API
 */
public class CurrentWeatherMeasurement {

    private long datetime;
    private String description;
    private Integer temperature;
    private Integer maxTemperature;
    private Integer minTemperature;
    private Integer humidity;
    private Integer pressure;
    private Double windSpeed;
    private Integer windDirection;

    public CurrentWeatherMeasurement() {
    }

    public CurrentWeatherMeasurement(long datetime, String description, Integer temperature, Integer humidity, Integer pressure, Double windSpeed, Integer windDirection) {
        this.datetime = datetime;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

}

