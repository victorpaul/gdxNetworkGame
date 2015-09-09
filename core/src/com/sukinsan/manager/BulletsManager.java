package com.sukinsan.manager;

import com.sukinsan.entity.Bullet;
import com.sukinsan.world.GameWorld;

import java.util.Iterator;

/**
 * Created by victor on 16.06.15.
 */
public class BulletsManager {
    private GameWorld gameWorld;

    public BulletsManager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void update(float delta){
        Iterator<Bullet> iter = gameWorld.getBullets().iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if(bullet.getX() > gameWorld.getCameraManager().position.x + gameWorld.getCameraManager().viewportWidth/2 || bullet.getX() < gameWorld.getCameraManager().position.x - gameWorld.getCameraManager().viewportWidth/2){
                bullet.dispose();
                iter.remove();
            }else{
                bullet.update(delta);
            }
        }
    }
}