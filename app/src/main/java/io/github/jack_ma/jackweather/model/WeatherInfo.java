package io.github.jack_ma.jackweather.model;

/**
 * Created by trys on 16-1-27.
 */
public class WeatherInfo {
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    private String desc;
    private String status;
    private Data data;
}
