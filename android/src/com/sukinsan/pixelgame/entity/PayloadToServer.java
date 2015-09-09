package com.sukinsan.pixelgame.entity;

import java.io.Serializable;

/**
 * Created by victor on 9/6/2015.
 */
public class PayloadToServer implements Serializable {
    public byte type;
    public float angle;
    public float range;

    public PayloadToServer(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PayloadToServer{" +
                "type=" + type +
                ", angle=" + angle +
                ", range=" + range +
                '}';
    }
}
