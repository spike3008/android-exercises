package com.android.madd.exercise1.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStatusChangeReceiver extends BroadcastReceiver {

    NetworkStatusHandler handler;

    public NetworkStatusChangeReceiver(NetworkStatusHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        handler.handleNetworkStatus(ConnectionDetector.isNetworkAvailable(context));
    }
}
