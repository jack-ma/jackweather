package io.github.jack_ma.jackweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.github.jack_ma.jackweather.service.AutoUpdateService;

/**
 * Created by trys on 16-1-23.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, AutoUpdateService.class);
            context.startService(i);
    }
}
