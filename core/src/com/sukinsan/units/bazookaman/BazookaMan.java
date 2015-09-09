package com.sukinsan.units.bazookaman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.sukinsan.entity.Bullet;
import com.sukinsan.entity.BaseUnit;
import com.sukinsan.world.GameWorld;
import com.sukinsan.entity.BaseUnitPart.*;

import java.util.Random;


/**
 * Created by victorpaul on 04/06/15.
 */
public class BazookaMan extends BaseUnit{

    private float shootEvery = 0.08f;
    private float fireDelay;
    Sound shootingSound;

    private int height = 35;
    private int width = 30;

    private BazookaManBodyPart body;

    public BazookaMan(GameWorld gameWorld,int x,int y) {
        super(gameWorld);
        setX(x * gameWorld.getCollisionLayer().getTileWidth());
        setY(y * gameWorld.getCollisionLayer().getTileHeight());

        body = new BazookaManBodyPart(this);
        addPart(body);

        shootingSound = Gdx.audio.newSound(Gdx.files.internal("scout_fire-1.wav"));
    }

    @Override
    public void dispose() {
        shootingSound.dispose();
        disposeParts();
    }

    public void shoot(float delta) {
        fireDelay -= delta;
        if (fireDelay <= 0) {
            Random rand = new Random();
            int max = (int)(height/1.5);
            int min = (int)(height/2);
            int randomNum = rand.nextInt((max - min) + 1) + min;

            Bullet bullet = new Bullet(isBodyDirectionLeft());
            bullet.setPosition(getX() + width/2,getY() + randomNum);
            getGameWorld().getBullets().add(bullet);
            fireDelay += shootEvery;
            shootingSound.play();
        }
    }

    @Override
    public void calculateAnimation(float delta) {
        if(isOnTheGround()){
            if(body.getOldAnimationStatus() == AnimationStatus.jumping){
                body.setAnimationStatus(AnimationStatus.endjumping);
            }
            if(getVelocity().x != 0){
                body.setAnimationStatus(AnimationStatus.running);
            }else if(body.getOldAnimationStatus() == AnimationStatus.running){
                body.setAnimationStatus(AnimationStatus.staing);
            }
        }else{
            body.setAnimationStatus(AnimationStatus.jumping);
        }

        if(body.getOldAnimationStatus() != body.getAnimationStatus()){
            body.setOldAnimationStatus(body.getAnimationStatus());
            body.resetAnimationState();
        }
    }

}