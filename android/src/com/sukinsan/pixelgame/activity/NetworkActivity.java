package com.sukinsan.pixelgame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.Connections;
import com.sukinsan.pixelgame.entity.EndpointInfo;
import com.sukinsan.pixelgame.entity.PayloadToServer;
import com.sukinsan.pixelgame.utils.SerializeUtils;
import com.sukinsan.pixelgame.utils.SystemUtils;

import java.io.IOException;

/**
 * Created by victor on 9/5/2015.
 */
abstract public class NetworkActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Connections.ConnectionRequestListener,
        Connections.MessageListener,
        Connections.EndpointDiscoveryListener{

    private static final String TAG = NetworkActivity.class.getSimpleName();

    public void sendPayLoad(GoogleApiClient mGoogleApiClient,EndpointInfo endpoint,PayloadToServer payload){
        Log.i(TAG, "sendPayLoad " + payload.toString());

        try {
            Nearby.Connections.sendReliableMessage(mGoogleApiClient, endpoint.getEndpointId(), SerializeUtils.serialize(payload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        String bund = bundle==null?"":bundle.toString();
        SystemUtils.toast(this, "onConnected(Bundle bundle) " + bund);
    }

    @Override
    public void onConnectionSuspended(int i) {
        SystemUtils.toast(this, "onConnectionSuspended(int i)" + i);
    }

    @Override
    public void onEndpointFound(final String endpointId, String deviceId,String serviceId, final String endpointName) {
        SystemUtils.toast(this, "onEndpointFound endpointId=" + endpointId + ". deviceId=" + deviceId + ". serviceId=" + serviceId + ". deviceName=" + endpointName);
    }

    @Override
    public void onEndpointLost(String s) {
        SystemUtils.toast(this, "onEndpointLost s=" + s);
    }

    @Override
    public void onMessageReceived(String s, byte[] bytes, boolean b) {
        PayloadToServer payload;
        try {
            payload = (PayloadToServer)SerializeUtils.deserialize(bytes);
            SystemUtils.toast(this, "onMessageReceived s=" + s + " bytes=" + payload.toString() + " b=" + b);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisconnected(String s) {
        SystemUtils.toast(this, "onDisconnected s=" + s);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        SystemUtils.toast(this, "onConnectionFailed =" + connectionResult);
    }
}
