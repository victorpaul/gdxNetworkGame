package com.sukinsan.pixelgame.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by victor on 9/5/2015.
 */
public class NetworkUtils {
    public static int[] NETWORK_TYPES = {ConnectivityManager.TYPE_WIFI,ConnectivityManager.TYPE_ETHERNET};

    public final static byte
            PAYLOAD_TYPE_ACTION = 0,
        PAYLOAD_TYPE_MOVE = 1;

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (int networkType : NETWORK_TYPES) {
            NetworkInfo info = connManager.getNetworkInfo(networkType);
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
}
