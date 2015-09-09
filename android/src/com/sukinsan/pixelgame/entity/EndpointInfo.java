package com.sukinsan.pixelgame.entity;

/**
 * Created by victor on 06.09.15.
 */
public class EndpointInfo {
    private String endpointId;
    private String deviceId;
    private String serviceId;
    private String endpointName;

    public EndpointInfo(String endpointId, String deviceId, String serviceId, String endpointName) {
        this.endpointId = endpointId;
        this.deviceId = deviceId;
        this.serviceId = serviceId;
        this.endpointName = endpointName;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getEndpointName() {
        return endpointName;
    }

    @Override
    public String toString() {
        return "EndpointInfo{" +
                "endpointId='" + endpointId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }
}
