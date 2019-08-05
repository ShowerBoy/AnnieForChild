package com.androidupnpdemo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PollingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
            Intent i = new Intent(context, PollingService.class);
            context.startService(i);
    }

}