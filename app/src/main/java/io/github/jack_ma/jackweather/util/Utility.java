package io.github.jack_ma.jackweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        WeatherInfo weatherInfo = gson.fromJson(response, WeatherInfo.class);
        saveWeatherInfo(weatherInfo, context);
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

    /**
     * 序列化对象
     *
     * @param weatherInfo
     * @return
     * @throws IOException
     */
    private static String serialize(WeatherInfo weatherInfo) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(weatherInfo);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static WeatherInfo deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        WeatherInfo weatherInfo = (WeatherInfo) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return weatherInfo;
    }

    public static void saveWeatherInfo (WeatherInfo weatherInfo, Context context) {
        String strObject = new String();
        try {
            strObject = serialize(weatherInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences sp = context.getSharedPreferences("weather_info", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("weather", strObject);
        edit.commit();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//        editor.putBoolean("city_selected", true);
//        editor.putString("current_date", sdf.format(new Date()));
//        editor.commit();
    }

    public static WeatherInfo getWeatherInfo (Context context) {

        SharedPreferences sp = context.getSharedPreferences("weather_info", 0);
        try {
            WeatherInfo weatherInfo = deSerialization(sp.getString("weather", null));
            return  weatherInfo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
