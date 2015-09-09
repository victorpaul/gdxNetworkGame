package com.sukinsan.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by victorpaul on 04/06/15.
 */
abstract public class BaseUnitPart extends Sprite {
    public enum AnimationStatus{staing,jumping,endjumping,running};

    private AnimationStatus animationStatus = AnimationStatus.staing;
    private AnimationStatus oldAnimationStatus = AnimationStatus.staing;
    private TextureRegion currentFrame;

    private BaseUnit unit;
    private boolean isCollised = false;
    private int relativX,relativY;
    private float animationState = 0f;

    public abstract void dispose();
    public abstract void calculateAnimation(float delta);
    public abstract void calculateAnimationEvents(float delta);

    public BaseUnitPart(BaseUnit unit, Texture texture,int width,int height) {
        super(texture,width,height);
        this.unit = unit;
    }

    public void dispose(TextureRegion[] textureRegions){
        for(TextureRegion tr: textureRegions){
            tr.getTexture().dispose();
        }
    }

    public BaseUnit getUnit() {
        return unit;
    }

    public void flipFrameToBodyDirection(){
        if(!getUnit().isBodyDirectionLeft() && !getCurrentFrame().isFlipX()){
            getCurrentFrame().flip(true, false);
        }else if(getUnit().isBodyDirectionLeft() && getCurrentFrame().isFlipX()){
            getCurrentFrame().flip(true, false);
        }
    }

    public boolean isCollised() {
        return isCollised;
    }

    public void setIsCollised(boolean isCollised) {
        this.isCollised = isCollised;
    }

    public int getRelativX() {
        return relativX;
    }

    public void setRelativX(int relativX) {
        this.relativX = relativX;
    }

    public int getRelativY() {
        return relativY;
    }

    public void setRelativY(int relativY) {
        this.relativY = relativY;
    }

    public float getAnimationState() {
        return animationState;
    }

    public void resetAnimationState() {
        this.animationState = 0;
    }

    public void increaseAnimationState(float delta){
        this.animationState += delta;
    }

    public AnimationStatus getAnimationStatus() {
        return animationStatus;
    }

    public void setAnimationStatus(AnimationStatus animationStatus) {
        this.animationStatus = animationStatus;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public AnimationStatus getOldAnimationStatus() {
        return oldAnimationStatus;
    }

    public void setOldAnimationStatus(AnimationStatus oldAnimationStatus) {
        this.oldAnimationStatus = oldAnimationStatus;
    }
}
