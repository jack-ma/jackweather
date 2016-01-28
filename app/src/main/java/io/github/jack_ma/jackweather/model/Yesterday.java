package io.github.jack_ma.jackweather.model;

import java.io.Serializable;

/**
 * Created by trys on 16-1-27.
 */
public class Yesterday implements Serializable{

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getfx() {
        return fx;
    }

    public void setfx(String fX) {
        this.fx = fX;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    @Override
    public String toString() {
        return "Yesterday{" +
                "fl='" + fl + '\'' +
                ", fX='" + fx + '\'' +
                ", high='" + high + '\'' +
                ", type='" + type + '\'' +
                ", low='" + low + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String fl;
    public String fx;
    public String high;
    public String type;
    public String low;
    public String date;
}
