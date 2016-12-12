package com.aiosdev.weather.model;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class WeatherInfo {
    private Weather mWeather;
    private Main mMain;
    private Wind mWind;
    private Clouds mClouds;

    public Weather getWeather() {
        return mWeather;
    }

    public void setWeather(Weather weather) {
        mWeather = weather;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public Clouds getClouds() {
        return mClouds;
    }

    public void setClouds(Clouds clouds) {
        mClouds = clouds;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "mWeather=" + mWeather +
                ", mMain=" + mMain +
                ", mWind=" + mWind +
                ", mClouds=" + mClouds +
                '}';
    }
}
