package com.example.senaiweather;

public class WheaterInfo {
    private String temp;
    private String climate;
    private String cityName;
    private String humidity;
    private String wind;
    public WheaterInfo(String temp, String climate, String cityName, String humidity, String wind) {
        this.temp = temp;
        this.climate = climate;
        this.cityName = cityName;
        this.humidity = humidity;
        this.wind = wind;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
