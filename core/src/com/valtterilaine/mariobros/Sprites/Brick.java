package com.valtterilaine.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.valtterilaine.mariobros.MarioBros;
import com.valtterilaine.mariobros.Scenes.HUD;
import com.valtterilaine.mariobros.Screens.PlayScreen;

/**
 * Created by Oasis3 on 30.5.2016.
 */

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("brick", "collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        HUD.addScore(200);
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }

}
