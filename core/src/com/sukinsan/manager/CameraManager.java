package com.sukinsan.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sukinsan.world.GameWorld;

/**
 * Created by victor on 16.06.15.
 */
public class CameraManager extends OrthographicCamera {
    private GameWorld gameWorld;

    public CameraManager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void update(){
        position.x = gameWorld.getPlayerManager().getBaseUnit().getX();

        super.update();
    }

    public void resize(float width,float height){
        float proportion = (width>height) ? width / height : height / width;

        viewportHeight = gameWorld.getCollisionLayer().getHeight() * gameWorld.getCollisionLayer().getTileHeight();
        viewportWidth = viewportHeight * proportion;

        position.y = viewportHeight / 2;

        System.out.println("proportion=" + proportion);
    }
}