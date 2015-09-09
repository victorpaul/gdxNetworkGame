package com.sukinsan.manager;

import com.sukinsan.entity.BaseUnit;
import com.sukinsan.world.GameWorld;

/**
 * Created by victor on 06.06.15.
 */
public class GravityManager {

    private GameWorld gameWorld;
    private float gravity;

    public GravityManager(GameWorld gameWorld, float gravity) {
        this.gameWorld = gameWorld;
        this.gravity = gravity;
    }

    public void applyGravity(BaseUnit unit, float delta){
        // apply gravity
        unit.getVelocity().y -= gravity * delta;

        //clamp velocity
        if(unit.getVelocity().y > unit.getJumpSpeed()){
            unit.getVelocity().y = unit.getJumpSpeed();
        }else if(unit.getVelocity().y < -unit.getJumpSpeed()){
            unit.getVelocity().y = -unit.getJumpSpeed();
        }
    }
}
