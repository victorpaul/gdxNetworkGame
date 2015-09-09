package com.sukinsan.pixelgame.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.Connections;
import com.sukinsan.pixelgame.R;
import com.sukinsan.pixelgame.entity.EndpointInfo;
import com.sukinsan.pixelgame.entity.Motion;
import com.sukinsan.pixelgame.entity.PayloadToServer;
import com.sukinsan.pixelgame.utils.NetworkUtils;
import com.sukinsan.pixelgame.utils.SystemUtils;

public class GamePadActivity extends NetworkActivity{
    private static final String TAG = GamePadActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private EndpointInfo serverEndpointInfo;

    private Motion moveMotion;
    private Motion actionMotion;

    private PayloadToServer movePayload = new PayloadToServer(NetworkUtils.PAYLOAD_TYPE_MOVE);
    private PayloadToServer actionPayload = new PayloadToServer(NetworkUtils.PAYLOAD_TYPE_ACTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        SystemUtils.initFullScreen(this);

        moveMotion = new Motion();
        actionMotion = new Motion();

        findViewById(R.id.sensor_move).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        moveMotion.setxStart(event.getX());
                        moveMotion.setyStart(event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        moveMotion.setxEnd(event.getX());
                        moveMotion.setyEnd(event.getY());

                        movePayload.angle = moveMotion.getAngle();
                        movePayload.range = moveMotion.getRange();
                        sendPayLoad(mGoogleApiClient, serverEndpointInfo, movePayload);
                        break;
                }

                return true;
            }
        });

        findViewById(R.id.sensor_fight).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        actionMotion.setxStart(event.getX());
                        actionMotion.setyStart(event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        actionMotion.setxEnd(event.getX());
                        actionMotion.setyEnd(event.getY());

                        actionPayload.angle = moveMotion.getAngle();
                        actionPayload.range = moveMotion.getRange();
                        sendPayLoad(mGoogleApiClient, serverEndpointInfo, actionPayload);
                        break;
                }
                return true;
            }
        });

        findViewById(R.id.btn_discover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiscovery();
            }
        });

        findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectTo(serverEndpointInfo, null);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Nearby.CONNECTIONS_API)
                .build();
    }

    private void startDiscovery() {
        if(!NetworkUtils.isConnectedToNetwork(this)){
            SystemUtils.toast(GamePadActivity.this, "!isConnectedToNetwork()");
            return;
        }

        String serviceId = getString(R.string.service_id);

        // Set an appropriate timeout length in milliseconds
        long DISCOVER_TIMEOUT = 1000L;

        // Discover nearby apps that are advertising with the required service ID.
        Nearby.Connections.startDiscovery(mGoogleApiClient, serviceId, DISCOVER_TIMEOUT, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            SystemUtils.toast(GamePadActivity.this, "status.isSuccess()");
                        } else {
                            int statusCode = status.getStatusCode();
                            SystemUtils.toast(GamePadActivity.this, "statusCode " + statusCode);
                        }
                    }
                });
    }

    private void connectTo(final EndpointInfo endpointInfo,String myName) {
        //Nearby.Connections.stopAllEndpoints(mGoogleApiClient);

        if(endpointInfo == null){
            SystemUtils.toast(GamePadActivity.this,"We didn't find any server");
            return;
        }

        byte[] myPayload = null;
        Nearby.Connections.sendConnectionRequest(mGoogleApiClient, myName, endpointInfo.getEndpointId(), myPayload, new Connections.ConnectionResponseCallback() {
            @Override
            public void onConnectionResponse(String remoteEndpointId, Status status, byte[] bytes) {
                if (status.isSuccess()) {
                    SystemUtils.toast(GamePadActivity.this, "Connected to " + endpointInfo.getEndpointName());
                } else {
                    SystemUtils.toast(GamePadActivity.this, "Couldn't connect to " + endpointInfo.getEndpointName());
                }
            }
        }, this);
    }

    @Override
    public void onConnectionRequest(final String remoteEndpointId, String remoteDeviceId,String remoteEndpointName, byte[] payload) {
        SystemUtils.toast(this, "onConnectionRequest remoteEndpointId=" + remoteEndpointId + ". remoteDeviceId=" + remoteDeviceId + ". remoteEndpointName=" + remoteEndpointName + ". payload=" + payload);
        Nearby.Connections.rejectConnectionRequest(mGoogleApiClient, remoteEndpointId);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        SystemUtils.restoreFullScreen(this, hasFocus);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            SystemUtils.toast(this, "mGoogleApiClient.disconnect()");
        }
    }

    @Override
    public void onEndpointFound(String endpointId, String deviceId, String serviceId, String endpointName) {
        serverEndpointInfo = new EndpointInfo(endpointId,deviceId,serviceId,endpointName);
        SystemUtils.toast(this,serverEndpointInfo.toString());
    }
}