package io.github.jack_ma.jackweather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trys on 16-1-27.
 */
public class Data implements Serializable{
    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Yesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    @Override
    public String toString() {
        return "Data{" +
                "wendu='" + wendu + '\'' +
                ", ganmao='" + ganmao + '\'' +
                ", forecast=" + forecast +
                ", aqi='" + aqi + '\'' +
                ", city='" + city + '\'' +
                ", yesterday=" + yesterday +
                '}';
    }

    private String wendu;
    private String ganmao;
    private List<Forecast> forecast;
    private String aqi;
    private String city;
    private Yesterday yesterday;
}
