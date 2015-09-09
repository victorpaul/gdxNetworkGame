package com.sukinsan.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.sukinsan.entity.Bullet;
import com.sukinsan.entity.BaseUnit;
import com.sukinsan.manager.CameraManager;
import com.sukinsan.manager.GravityManager;
import com.sukinsan.manager.PlayerManager;

import java.util.ArrayList;

/**
 * Created by victor on 02.06.15.
 */
public interface GameWorld {

    public TiledMapTileLayer getCollisionLayer();

    public GravityManager getGravityManager();
    public TiledMap getTiledMap();
    public CameraManager getCameraManager();
    public PlayerManager getPlayerManager();

    public ArrayList<BaseUnit> getEnemies();
    public ArrayList<Bullet> getBullets();
}
