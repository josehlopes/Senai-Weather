package com.example.senaiweather;

public class CityINFO {
    private String temp;
    private String country;
    private String humidity;

    private String windSpeed;

    private String icon;

    public CityINFO(String temp, String country, String humidity, String windSpeed, String icon) {
        this.temp = temp;
        this.country = country;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
