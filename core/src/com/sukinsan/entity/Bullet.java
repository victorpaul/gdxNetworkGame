package com.sukinsan.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by victor on 27.05.15.
 */
public class Bullet extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 60 * 5f;

    private Animation flyingAnimation;
    private TextureRegion[] flying;
    private float animationState = 0f;

    private TextureRegion currentFrame;

    public void dispose(){
        dispose(flying);
        if(currentFrame != null) { // TODO prevent creating of bullets that have never been drawen
            currentFrame.getTexture().dispose();
        }
        getTexture().dispose();
    }

    private void dispose(TextureRegion[] textureRegions){
        for(TextureRegion tr: textureRegions){
            tr.getTexture().dispose();
        }
    }

    public Bullet(boolean directionLeft) {
        super(new Sprite(new Texture("bullet.png")),0,0,10,6);

        flying = new TextureRegion[8];
        flying[0] = new TextureRegion(getTexture(),0,0,15,9);
        flying[1] = new TextureRegion(getTexture(),17,0,15,9);
        flying[2] = new TextureRegion(getTexture(),34,0,15,9);
        flying[3] = new TextureRegion(getTexture(),51,0,15,9);
        flying[4] = new TextureRegion(getTexture(),68,0,15,9);
        flying[5] = new TextureRegion(getTexture(),85,0,15,9);
        flying[6] = new TextureRegion(getTexture(),102,0,15,9);
        flying[7] = new TextureRegion(getTexture(),119,0,15,9);


        int range = 40;
        int min = directionLeft ? 160 : -20;
        int max = min+range;

        flyingAnimation = new Animation(0.055f,flying);

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        setRotation(randomNum);
        double r = getRotation() * (Math.PI/180f);
        velocity.x = (float) (Math.cos(r) * speed);
        velocity.y = (float) (Math.sin(r) * speed);

        setCenter(getWidth()/2,getHeight()/2);

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void update(float delta) {
        currentFrame = flyingAnimation.getKeyFrame(animationState);
        setRegion(currentFrame);

        setX(getX() +  velocity.x * delta);
        setY(getY() + velocity.y * delta);
        animationState += delta;
    }

}
