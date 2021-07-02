package com.example.libliu;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.RequiresPermission;


import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static com.example.libliu.NetworkUtils.getNetworkType;


public final class NetworkChangedReceiver extends BroadcastReceiver {

    public static NetworkChangedReceiver getInstance() {
        return LazyHolder.INSTANCE;
    }

    private NetworkUtils.NetworkType mType;
    private NetworkUtils.OnNetworkStatusChangedListener mListener;

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public void registerListener(final NetworkUtils.OnNetworkStatusChangedListener listener) {
        if (listener == null) {
            return;
        }
        mListener = listener;
        ThreadUtils.runOnUiThread(() -> {
            mType = getNetworkType();
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            AppUtils.getApp().registerReceiver(NetworkChangedReceiver.getInstance(), intentFilter);
        });
    }

    public void unregisterListener() {
        ThreadUtils.runOnUiThread(() -> {
            AppUtils.getApp().unregisterReceiver(NetworkChangedReceiver.getInstance());
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            // 去除网络抖动
            ThreadUtils.runOnUiThreadDelayed(() -> {
                NetworkUtils.NetworkType networkType = getNetworkType();
                if (mType == networkType) {
                    return;
                }
                mType = networkType;

                if (networkType == NetworkUtils.NetworkType.NETWORK_NO) {
                    mListener.onDisconnected();
                } else {
                    mListener.onConnected(networkType);
                }
            }, 1000);
        }
    }

    private static class LazyHolder {
        private static final NetworkChangedReceiver INSTANCE = new NetworkChangedReceiver();
    }
}