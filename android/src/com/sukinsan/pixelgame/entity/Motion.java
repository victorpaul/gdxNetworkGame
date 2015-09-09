package com.sukinsan.pixelgame.entity;

/**
 * Created by victor on 9/5/2015.
 */
public class Motion {
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;

    public Motion() {

    }

    public float getRange(){
        return (float)Math.sqrt( (xStart - xEnd) * (xStart - xEnd) + (yStart - yEnd) * (yStart - yEnd) );
    }

    public float getAngle(){
        float dx = xEnd - xStart;
        float dy = yEnd - yStart;
        float rads = (float)Math.atan2(dy,dx);

        if (rads < 0){
            rads += (2*Math.PI);
        }
        return (float)(rads/(Math.PI/180));
    }

    public float getxStart() {
        return xStart;
    }

    public void setxStart(float xStart) {
        this.xStart = xStart;
    }

    public float getyStart() {
        return yStart;
    }

    public void setyStart(float yStart) {
        this.yStart = yStart;
    }

    public float getxEnd() {
        return xEnd;
    }

    public void setxEnd(float xEnd) {
        this.xEnd = xEnd;
    }

    public float getyEnd() {
        return yEnd;
    }

    public void setyEnd(float yEnd) {
        this.yEnd = yEnd;
    }

    @Override
    public String toString() {
        return "Motion{" +
                "xStart=" + xStart +
                ", yStart=" + yStart +
                ", xEnd=" + xEnd +
                ", yEnd=" + yEnd +
                '}';
    }
}
