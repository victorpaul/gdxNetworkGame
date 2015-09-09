package com.sukinsan.units.bazookaman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sukinsan.entity.BaseUnit;
import com.sukinsan.entity.BaseUnitPart;

/**
 * Created by victorpaul on 04/06/15.
 */
public class BazookaManBodyPart extends BaseUnitPart{
    private Animation standAnimation,walkAnimationLeft,jumpingAnimation,endJumpingAnimation;
    private TextureRegion[] standing, walking,jumping,endJumping;

    public BazookaManBodyPart(BaseUnit unit) {
        super(unit,new Texture("solder.png"), 20, 30);

        Texture textureSheet = getTexture();

        setRelativX(0);
        setRelativY(0);

        int y = 0;
        int height = 45;
        standing = new TextureRegion[6];
        standing[0] = new TextureRegion(textureSheet, 0,0,39,height);
        standing[1] = new TextureRegion(textureSheet,41,0,40,height);
        standing[2] = new TextureRegion(textureSheet,83,0,40,height);
        standing[3] = new TextureRegion(textureSheet,123,0,40,height);
        standing[4] = new TextureRegion(textureSheet,163,0,40,height);
        standing[5] = new TextureRegion(textureSheet,203,0,40,height);

        y = 50;
        height = 91 - y;
        walking = new TextureRegion[11];
        walking[0] = new TextureRegion(textureSheet,2 , y ,44,height);
        walking[1] = new TextureRegion(textureSheet,46, y  ,44,height);
        walking[2] = new TextureRegion(textureSheet,90, y  ,43,height);
        walking[3] = new TextureRegion(textureSheet,133,y  ,44,height);
        walking[4] = new TextureRegion(textureSheet,177,y  ,43,height);
        walking[5] = new TextureRegion(textureSheet,220,y  ,44,height);
        walking[6] = new TextureRegion(textureSheet,264,y  ,44,height);
        walking[7] = new TextureRegion(textureSheet,308,y  ,41,height);
        walking[8] = new TextureRegion(textureSheet,349,y  ,40,height);
        walking[9] = new TextureRegion(textureSheet,389,y  ,40,height);
        walking[10] = new TextureRegion(textureSheet,428,y  ,44,height);

        y = 191;
        height = 233 - y;
        jumping = new TextureRegion[7];
        jumping[0] = new TextureRegion(textureSheet,88,y,46,height);
        jumping[1] = new TextureRegion(textureSheet,134,y,46,height);
        jumping[2] = new TextureRegion(textureSheet,180,y,45,height);
        jumping[3] = new TextureRegion(textureSheet,225,y,43,height);
        jumping[4] = jumping[2];
        jumping[5] = jumping[1];
        jumping[6] = jumping[0];

        endJumping = new TextureRegion[2];
        endJumping[0] = new TextureRegion(textureSheet,268,y,41,height);
        endJumping[1] = new TextureRegion(textureSheet,309,y,41,height);

        float animationSpeed = 0.125f;

        standAnimation = new Animation(animationSpeed, standing);
        walkAnimationLeft = new Animation(animationSpeed, walking);
        jumpingAnimation = new Animation(animationSpeed,jumping);
        endJumpingAnimation = new Animation(animationSpeed,endJumping);


    }

    @Override
    public void dispose(){
        dispose(standing);
        dispose(walking);
        dispose(jumping);
        dispose(endJumping);
        getCurrentFrame().getTexture().dispose();
        getTexture().dispose();
    }

    @Override
    public void calculateAnimation(float delta) {
        if(getAnimationStatus() == AnimationStatus.jumping){
            setCurrentFrame(jumpingAnimation.getKeyFrame(getAnimationState()));
        }
        if (getAnimationStatus() == AnimationStatus.staing) {
            setCurrentFrame(standAnimation.getKeyFrame(getAnimationState(), true));
        }
        if(getAnimationStatus() == AnimationStatus.running) {
            setCurrentFrame(walkAnimationLeft.getKeyFrame(getAnimationState(), true));
        }
        if(getAnimationStatus() == AnimationStatus.endjumping) {
            setCurrentFrame(endJumpingAnimation.getKeyFrame(getAnimationState()));
        }

        flipFrameToBodyDirection();

        setRegion(getCurrentFrame());
    }

    @Override
    public void calculateAnimationEvents(float delta) {
        if(getAnimationStatus() == AnimationStatus.endjumping) {
            if (endJumpingAnimation.isAnimationFinished(getAnimationState())){
                setAnimationStatus(AnimationStatus.staing);
            }
        }
    }
}
