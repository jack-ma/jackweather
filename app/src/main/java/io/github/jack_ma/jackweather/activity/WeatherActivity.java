package io.github.jack_ma.jackweather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.github.jack_ma.jackweather.R;
import io.github.jack_ma.jackweather.model.WeatherInfo;
import io.github.jack_ma.jackweather.service.AutoUpdateService;
import io.github.jack_ma.jackweather.util.HttpCallbackListener;
import io.github.jack_ma.jackweather.util.HttpUtil;
import io.github.jack_ma.jackweather.util.Utility;

/**
 * Created by trys on 16-1-22.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {
    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private  TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        String countyName = getIntent().getStringExtra("county_name");
        if (!TextUtils.isEmpty(countyName)) {
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            try {
                queryWeatherInfo(countyName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            showWeather(WeatherActivity.this);
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                WeatherInfo weatherInfo = Utility.getWeatherInfo(WeatherActivity.this);
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String countyName = weatherInfo.getData().getCity();
                if (!TextUtils.isEmpty(countyName)) {
                    try {
                        queryWeatherInfo(countyName);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
 //   http://wthrcdn.etouch.cn/weather_mini?city=%E6%B5%B7%E6%B7%80
    private void queryWeatherInfo (String countyName) throws UnsupportedEncodingException {
        String address = "http://wthrcdn.etouch.cn/weather_mini?city=" + URLEncoder.encode(countyName, "utf8");
        queryFromServer(address);
    }

    private void queryFromServer(final String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather(WeatherActivity.this);
                        }
                    });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }

    private void showWeather(Context context) {
        WeatherInfo weatherInfo = Utility.getWeatherInfo(context);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(weatherInfo.getData().getCity());
        temp1Text.setText(weatherInfo.getData().getForecast().get(0).getLow());
        temp2Text.setText(weatherInfo.getData().getForecast().get(0).getHigh());
        weatherDespText.setText(weatherInfo.getData().getForecast().get(0).getType());
        // TODO: 16-1-28 距离上次更新时间
        publishText.setText("同步完成");
        currentDateText.setText(weatherInfo.getData().getForecast().get(0).getDate());
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
}
