package io.github.jack_ma.jackweather.util;

/**
 * Created by trys on 16-1-19.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
