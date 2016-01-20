package io.github.jack_ma.jackweather.util;

import android.text.TextUtils;

import io.github.jack_ma.jackweather.model.City;
import io.github.jack_ma.jackweather.model.County;
import io.github.jack_ma.jackweather.model.JackWeatherDB;
import io.github.jack_ma.jackweather.model.Province;

/**
 * Created by trys on 16-1-20.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse (JackWeatherDB jackWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    jackWeatherDB.saveProvince(province);
                }

                return true;
            }

        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse (JackWeatherDB jackWeatherDB, String response, int ProvinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    jackWeatherDB.saveCity(city);
                }

                return true;
            }

        }
        return false;
    }

    public synchronized static boolean handleCountiesResponse (JackWeatherDB jackWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    jackWeatherDB.saveCounty(county);
                }

                return true;
            }

        }
        return false;
    }
}
