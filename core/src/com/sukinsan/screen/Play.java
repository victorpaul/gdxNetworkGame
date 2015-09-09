package com.sukinsan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.*;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sukinsan.entity.Bullet;
import com.sukinsan.entity.BaseUnit;
import com.sukinsan.manager.BulletsManager;
import com.sukinsan.manager.CameraManager;
import com.sukinsan.manager.EnemyManager;
import com.sukinsan.manager.PlayerManager;
import com.sukinsan.world.GameWorld;
import com.sukinsan.units.bazookaman.BazookaMan;
import com.sukinsan.manager.GravityManager;

import java.util.ArrayList;

/**
 * Created by victorpaul on 21/05/15.
 */
public class Play implements Screen, GameWorld, InputProcessor{

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private CameraManager camera;

    private GravityManager gravityManager;
    private PlayerManager player;
    private EnemyManager enemyManager;
    private BulletsManager bulletsManager;

    private ArrayList<BaseUnit> units;
    private ArrayList<Bullet> bullets;

    public TiledMapTileLayer collisionLayer;

    @Override
    public void show() {
        gravityManager = new GravityManager(this,125 * 1.8f);
        bullets = new ArrayList<Bullet>();
        units = new ArrayList<BaseUnit>();

        camera = new CameraManager(this);
        enemyManager = new EnemyManager(this);
        bulletsManager = new BulletsManager(this);

        Gdx.input.setInputProcessor(this);
        //Gdx.audio.newMusic(Gdx.files.internal("doom2.mp3")).play();

        map = new TmxMapLoader().load("level_alien2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("collision");

        player = new PlayerManager(new BazookaMan(this,1,5));


        units.add(new BazookaMan(this,3,10));


        /*
        Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);

        //getting the frametiles
        Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("tiles").iterator();
        while(tiles.hasNext()){
            TiledMapTile tile = tiles.next();
            if(tile.getProperties().containsKey("animation") && tile.getProperties().get("animation",String.class).equals("flower")){
                frameTiles.add((StaticTiledMapTile) tile);
            }
        }

        // create the animated tile
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1 / 3f,frameTiles);
        for(TiledMapTile tile :frameTiles){
            animatedTile.getProperties().putAll(tile.getProperties());
        }//*/

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // UPDATE
        camera.update();
        enemyManager.updateEnemies(delta); // update enemy
        player.update(delta); // update player
        bulletsManager.update(delta); // update bullets

        // RENDER
        renderer.setView(camera);
        renderer.getBatch().begin();
        //AnimatedTiledMapTile.updateAnimationBaseTime();
        //renderer.renderImageLayer((TiledMapImageLayer) map.getLayers().get("background"));  // draw background
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("collision"));     // draw collision layer

        for(BaseUnit bu:units){bu.draw(renderer.getBatch());}           // draw enemies
        player.draw(renderer.getBatch());                               // draw player
        for(Bullet bullet:bullets){bullet.draw(renderer.getBatch());}   // draw bullets

        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        for(Bullet bullet : bullets){bullet.dispose();}
        for(BaseUnit bu:units){bu.dispose();}
        player.dispose();
    }

    @Override
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }
    @Override
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    @Override
    public ArrayList<BaseUnit> getEnemies() { return units; }
    @Override
    public GravityManager getGravityManager() { return gravityManager; }

    @Override
    public TiledMap getTiledMap() { return map; }

    @Override
    public CameraManager getCameraManager() { return camera; }
    @Override
    public PlayerManager getPlayerManager() { return player; }

    @Override
    public boolean keyDown(int keycode) {
        player.keyDown(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.keyUp(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
