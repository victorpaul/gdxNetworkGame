package com.sukinsan.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.sukinsan.world.GameWorld;

import java.util.ArrayList;

/**
 * Created by victor on 02.06.15.
 */
abstract public class BaseUnit{
    private GameWorld gameWorld;
    private Vector2 velocity = new Vector2();

    private ArrayList<BaseUnitPart> parts;
    private float x,y;

    private float jumpSpeed = 60 * 2.5f;
    private float speed = 60 * 2;
    private boolean bodyDirectionLeft = true;
    private boolean onTheGround = false;

    public BaseUnit(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        parts = new ArrayList<BaseUnitPart>();
    }

    public void addPart(BaseUnitPart part){
        parts.add(part);
    }

    public abstract void dispose();
    public abstract void calculateAnimation(float delta);

    public void disposeParts(){
        for(BaseUnitPart part: parts){
            part.dispose();
        }
    }

    public void update(float delta){
        calculateAnimation(delta);

        for(BaseUnitPart part: parts){
            part.calculateAnimation(delta);
            part.calculateAnimationEvents(delta);
            part.increaseAnimationState(delta);

            part.setX(getX() + part.getRelativX());
            part.setX(getY() + part.getRelativY());
        }

        getGameWorld().getGravityManager().applyGravity(this,delta);

        handleCollisionHorizontal(delta);
        handleCollisionVertical(delta);

        for(BaseUnitPart part: parts){
            part.setX(getX() + part.getRelativX());
            part.setY(getY() + part.getRelativY());
        }
    }

    public void draw(Batch batch){
        for(BaseUnitPart part: parts){
            part.draw(batch);
        }
    }

    public void handleCollisionHorizontal(float delta){ // TODO collision of the sprite and tilemap
        if(velocity.x == 0) {
            return;
        }
        boolean collisionX = false;
        float oldX = getX();
        float yStep = getGameWorld().getCollisionLayer().getTileHeight();
        setX(getX() + getVelocity().x * delta);
        for(BaseUnitPart part: parts){
            if(part.isCollised()){
                continue;
            }

            float yTarget = getY() + part.getHeight();
            float nextX = (getVelocity().x < 0) ? getX() + part.getRelativX(): getX() + part.getRelativX() + part.getWidth();
            for(float yIndex = getY();yIndex <= yTarget;  yIndex += yStep){
                collisionX = collisionX || isCollisionOnCell(nextX,yIndex);
                if(collisionX){
                    break;
                }
            }
            collisionX = collisionX || isCollisionOnCell(nextX,yTarget);

            if(collisionX){
                setX(oldX);
                getVelocity().x = 0;
                return;
            }
        }
    }

    public void handleCollisionVertical(float delta){
        if(velocity.y == 0) {
            return;
        }
        boolean collisionY = false;
        float oldY = getY();
        float xStep = getGameWorld().getCollisionLayer().getTileWidth();
        setY(getY() + getVelocity().y * delta);
        for(BaseUnitPart part: parts) {
            if(part.isCollised()){
                continue;
            }

            float xTarget = getX() + part.getRelativX() + part.getWidth();
            float nextY = (getVelocity().y < 0) ? getY() + part.getRelativY() : getY() + part.getRelativY() + part.getHeight();
            for(float xIndex = getX()+part.getRelativX();xIndex <=xTarget;  xIndex += xStep){  // TODO need to be optimized
                collisionY = collisionY || isCollisionOnCell(xIndex,nextY);
                if(collisionY){
                    break;
                }
            }
            collisionY = collisionY || isCollisionOnCell(xTarget,nextY);

            if (collisionY){
                if (getVelocity().y < 0) {
                    setOnTheGround(true);
                }
                setY(oldY);
                getVelocity().y = 0;
                return;
            }
        }
    }

    public void jump(){
        if(isOnTheGround()) {
            setOnTheGround(false);
            getVelocity().y = jumpSpeed;
        }
    }

    public void runLeft(){
        getVelocity().x = -speed;
        setBodyDirectionLeft(true);
    }

    public void runRight(){
        getVelocity().x = speed;
        setBodyDirectionLeft(false);
    }

    public void stopRunning(){
        getVelocity().x = 0;
    }

    public boolean isCollisionOnCell(float unitX,float unitY){
        Cell cell = getGameWorld().getCollisionLayer().getCell((int) (unitX / getGameWorld().getCollisionLayer().getTileWidth()), (int) (unitY / getGameWorld().getCollisionLayer().getTileHeight()) );
        return (cell!= null) && cell.getTile() != null;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public boolean isBodyDirectionLeft() {
        return bodyDirectionLeft;
    }

    public void setBodyDirectionLeft(boolean bodyDirectionLeft) {
        this.bodyDirectionLeft = bodyDirectionLeft;
    }

    public boolean isOnTheGround() {
        return onTheGround;
    }

    public void setOnTheGround(boolean onTheGround) {
        this.onTheGround = onTheGround;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }
}