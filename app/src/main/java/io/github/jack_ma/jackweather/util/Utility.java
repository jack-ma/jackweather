package io.github.jack_ma.jackweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.jack_ma.jackweather.model.City;
import io.github.jack_ma.jackweather.model.County;
import io.github.jack_ma.jackweather.model.JackWeatherDB;
import io.github.jack_ma.jackweather.model.Province;
import io.github.jack_ma.jackweather.model.WeatherInfo;

/**
 * Created by trys on 16-1-20.
 */
public class Utility {

    public static void handleWeatherResponse(Context context, String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//            String cityName = weatherInfo.getString("city");
//            String weatherCode = weatherInfo.getString("cityid");
//            String temp1 = weatherInfo.getString("temp1");
//            String temp2 = weatherInfo.getString("temp2");
//            String weatherDesp = weatherInfo.getString("weather");
//            String publishTime = weatherInfo.getString("ptime");
//            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp,publishTime);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Gson gson = new Gson();
        // java.lang.reflect.Type type = new TypeToken<JsonBean>(){}.getType();
        WeatherInfo jsonBean = gson.fromJson(response, WeatherInfo.class);
        saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp,publishTime);
    }

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

    public synchronized static boolean handleCitiesResponse (JackWeatherDB jackWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
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
                    county.setCityId(cityId);
                    jackWeatherDB.saveCounty(county);
                }

                return true;
            }

        }
        return false;
    }

    public static void saveWeatherInfo (Context context, String cityName, String weatherCode, String temp1,
                                        String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }
}
