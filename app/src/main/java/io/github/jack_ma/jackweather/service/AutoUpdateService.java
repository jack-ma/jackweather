package io.github.jack_ma.jackweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.github.jack_ma.jackweather.activity.WeatherActivity;
import io.github.jack_ma.jackweather.model.WeatherInfo;
import io.github.jack_ma.jackweather.util.HttpCallbackListener;
import io.github.jack_ma.jackweather.util.HttpUtil;
import io.github.jack_ma.jackweather.util.Utility;

/**
 * Created by trys on 16-1-23.
 */
public class AutoUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8*60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        WeatherInfo weatherInfo = Utility.getWeatherInfo(this);
        String countyName = weatherInfo.getData().getCity();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String weatherCode = prefs.getString("weather_code", "");
        String address = null;
        try {
            address = "http://wthrcdn.etouch.cn/weather_mini?city=" + URLEncoder.encode(countyName, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
