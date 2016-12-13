package com.aiosdev.weather.model;

import java.io.Serializable;
import java.util.List;

/**
 * 城市天气信息显示类
 * 包括4个子类
 */

public class WeatherInfo implements Serializable {
    private List<Weather> mWeather;
    private Main mMain;
    private Wind mWind;
    private Clouds mClouds;

    public List<Weather> getWeather() {
        return mWeather;
    }

    public void setWeather(List<Weather> weather) {
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
