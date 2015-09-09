package com.sukinsan.pixelgame.activity;

import android.os.Bundle;

import android.os.Handler;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AppIdentifier;
import com.google.android.gms.nearby.connection.AppMetadata;
import com.google.android.gms.nearby.connection.Connections;
import com.sukinsan.game.UAGdxGame;
import com.sukinsan.pixelgame.entity.EndpointInfo;
import com.sukinsan.pixelgame.entity.PayloadToServer;
import com.sukinsan.pixelgame.utils.NetworkUtils;
import com.sukinsan.pixelgame.utils.SerializeUtils;
import com.sukinsan.pixelgame.utils.SystemUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerActivity extends AndroidApplication implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Connections.ConnectionRequestListener,
        Connections.MessageListener,
        Connections.EndpointDiscoveryListener{

    private static final String TAG = ServerActivity.class.getSimpleName();
    // Identify if the device is the host
    private boolean mIsHost = false;
    private GoogleApiClient mGoogleApiClient;

    private UAGdxGame game;

    private ArrayList<EndpointInfo> clients = new ArrayList<EndpointInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //SystemUtils.initFullScreen(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Nearby.CONNECTIONS_API)
                .build();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new UAGdxGame();
        initialize(game, config);
    }

    private void startAdvertising() {
        SystemUtils.toast(ServerActivity.this, "startAdvertising()");
        if(!NetworkUtils.isConnectedToNetwork(this)){
            SystemUtils.toast(ServerActivity.this,"!isConnectedToNetwork()");
            //return;
        }

        // Identify that this device is the host
        mIsHost = true;

        // Advertising with an AppIdentifer lets other devices on the
        // network discover this application and prompt the user to
        // install the application.
        List<AppIdentifier> appIdentifierList = new ArrayList<>();
        appIdentifierList.add(new AppIdentifier(getPackageName()));
        AppMetadata appMetadata = new AppMetadata(appIdentifierList);

        // The advertising timeout is set to run indefinitely
        // Positive values represent timeout in milliseconds
        long NO_TIMEOUT = 0L;

        String name = null;
        Nearby.Connections.startAdvertising(mGoogleApiClient, name, appMetadata, NO_TIMEOUT,
                this).setResultCallback(new ResultCallback<Connections.StartAdvertisingResult>() {
            @Override
            public void onResult(Connections.StartAdvertisingResult result) {
                if (result.getStatus().isSuccess()) {
                    SystemUtils.toast(ServerActivity.this,"Device is advertising");
                } else {
                    int statusCode = result.getStatus().getStatusCode();
                    SystemUtils.toast(ServerActivity.this, "Advertising failed - see statusCode for more details");
                }
            }
        });
    }

    @Override
    public void onConnectionRequest(final String remoteEndpointId, final String remoteDeviceId,
                                    final String remoteEndpointName, byte[] payload) {
        if (mIsHost) {
            byte[] myPayload = null;
            // Automatically accept all requests
            Nearby.Connections.acceptConnectionRequest(mGoogleApiClient, remoteEndpointId,
                    myPayload, this).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        clients.add(new EndpointInfo(remoteEndpointId, remoteDeviceId, null, remoteEndpointName));
                        SystemUtils.toast(ServerActivity.this, "Connected to " + remoteEndpointName);
                    } else {
                        SystemUtils.toast(ServerActivity.this, "Failed to connect to: " + remoteEndpointName);
                    }
                }
            });
        } else {
            // Clients should not be advertising and will reject all connection requests.
            Nearby.Connections.rejectConnectionRequest(mGoogleApiClient, remoteEndpointId);
        }
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
    public void onConnected(Bundle bundle) {
        String bund = bundle==null?"":bundle.toString();
        SystemUtils.toast(this, "onConnected(Bundle bundle) " + bund);
        startAdvertising();
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
            payload = (PayloadToServer) SerializeUtils.deserialize(bytes);
            SystemUtils.toast(this, "onMessageReceived s=" + s + " bytes=" + payload.toString() + " b=" + b);

            if(payload.type == NetworkUtils.PAYLOAD_TYPE_MOVE) {
                if (payload.angle > 0 && payload.angle < 90) {
                    game.play.keyDown(Input.Keys.D);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game.play.keyUp(Input.Keys.D);
                        }
                    },(long)payload.range);
                }
                if (payload.angle > 90 && payload.angle < 180) {
                    game.play.keyDown(Input.Keys.A);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game.play.keyUp(Input.Keys.A);
                        }
                    },(long)payload.range);
                }

                if (payload.angle > 180 && payload.angle < 360) {
                    game.play.keyDown(Input.Keys.W);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game.play.keyUp(Input.Keys.W);
                        }
                    },(long)payload.range);
                }
            }

            if(payload.type == NetworkUtils.PAYLOAD_TYPE_ACTION) {
                game.play.keyDown(Input.Keys.SPACE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game.play.keyUp(Input.Keys.SPACE);
                    }
                },(long)payload.range);
            }


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
