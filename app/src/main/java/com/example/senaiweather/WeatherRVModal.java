package com.example.senaiweather;

public class WeatherRVModal {

    private String time;
    private String temper;
    private String img;
    private String wind;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
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

    public WeatherRVModal(String time, String temper, String img, String wind) {
        this.time = time;
        this.temper = temper;
        this.img = img;
        this.wind = wind;
    }
}
