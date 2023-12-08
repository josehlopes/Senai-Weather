package com.example.senaiweather;

public class WeatherDaysRVModal {
    private String date;
    private String temper;
    private String img;
    private String wind;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temper;
    }

    public void setTemperature(String temperature) {
        this.temper = temperature;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public WeatherDaysRVModal(String date, String temper, String img, String wind) {
        this.date = date;
        this.temper = temper;
        this.img = img;
        this.wind = wind;
    }
}
