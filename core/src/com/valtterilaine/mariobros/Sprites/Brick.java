package com.valtterilaine.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Oasis3 on 30.5.2016.
 */

public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);


        //"fixture is null"
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("brick", "collision");
    }
}
