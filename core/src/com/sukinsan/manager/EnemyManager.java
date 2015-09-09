package com.sukinsan.manager;

import com.sukinsan.entity.BaseUnit;
import com.sukinsan.units.bazookaman.BazookaMan;
import com.sukinsan.world.GameWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by victor on 06.06.15.
 */
public class EnemyManager {
    private GameWorld gameWorld;

    public EnemyManager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void updateEnemies(float delta){
        Random rand = new Random();
        int max = 3;
        int min = 0;
        for(BaseUnit unit:gameWorld.getEnemies()){
            if(unit instanceof BazookaMan){
                switch(rand.nextInt((max - min) + 1) + min){
                    case 0:
                        ((BazookaMan)unit).jump();
                        break;
                    case 1:
                        //((BazookaMan)unit).shoot(delta);
                        break;
                    case 2:
                        ((BazookaMan)unit).runRight();
                        break;
                    case 3:
                        ((BazookaMan)unit).runLeft();
                        break;

                }
            }
            unit.update(delta);
        }

    }

}
