package com.valtterilaine.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.valtterilaine.mariobros.MarioBros;
import com.valtterilaine.mariobros.Screens.PlayScreen;

/**
 * Created by Oasis3 on 30.5.2016.
 */

public class Mario extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float statetimer;
    private boolean runningRight;

    public Mario(PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        statetimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), 384 + (i * 16), 0, 16, 16)); //+ 384?
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), 384 + (i * 16), 0, 16, 16));
        }

        marioJump = new Animation(0.1f, frames);
        marioStand = new TextureRegion(getTexture(), 384, 0, 16, 16);

        defineMario();
        setBounds(384,0,16 / MarioBros.PPM, 16 / MarioBros.PPM); //Should the first zero be 384?
        setRegion(marioStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = marioJump.getKeyFrame(statetimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(statetimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        statetimer = currentState == previousState ? statetimer + dt : 0;  //Wut?
        previousState = currentState;
        return region;
    }

    public State getState() {

        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}