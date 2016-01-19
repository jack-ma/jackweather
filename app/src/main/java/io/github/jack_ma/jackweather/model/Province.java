package io.github.jack_ma.jackweather.model;

/**
 * Created by trys on 16-1-18.
 */
public class Province {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    private int id;
    private String provinceName;
    private String provinceCode;
}
