package com.sukinsan.manager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sukinsan.entity.BaseUnit;
import com.sukinsan.units.bazookaman.BazookaMan;

/**
 * Created by victor on 06.06.15.
 */
public class PlayerManager implements InputProcessor{

    private boolean pressedJump = false;
    private boolean pressedRunLeft = false;
    private boolean pressedRunRight = false;
    private boolean pressedFire = false;

    private BaseUnit baseUnit;

    public PlayerManager(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BaseUnit getBaseUnit() {
        return baseUnit;
    }

    public void update(float delta){
        baseUnit.stopRunning();
        if(pressedFire){
            if(baseUnit instanceof BazookaMan) {
                ((BazookaMan)baseUnit).shoot(delta);
            }
        }
        if(pressedJump){
            baseUnit.jump();
        }
        if(pressedRunLeft && !pressedRunRight){
            baseUnit.runLeft();
            baseUnit.setBodyDirectionLeft(true);
        }else if(!pressedRunLeft && pressedRunRight){
            baseUnit.runRight();
            baseUnit.setBodyDirectionLeft(false);
        }
        getBaseUnit().update(delta);
    }

    public void draw(Batch batch){
        getBaseUnit().draw(batch);
    }

    public void dispose(){
        getBaseUnit().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.W:
                pressedJump = true;
                break;
            case Input.Keys.A:
                pressedRunLeft = true;
                break;
            case Input.Keys.D:
                pressedRunRight = true;
                break;
            case Input.Keys.SPACE:
                pressedFire = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.W:
                pressedJump = false;
                break;
            case Input.Keys.A:
                pressedRunLeft = false;
                break;
            case Input.Keys.D:
                pressedRunRight = false;
                break;
            case Input.Keys.SPACE:
                pressedFire = false;
                break;
        }
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
