package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;

import javax.swing.text.StyledEditorKit;

public class Character {

    // Configuration constants
    public static final int FRAME_INTERVAL = 100;
    public static final int FRAME_COUNT = 4;

    // Core instance
    Core core;

    // Character variables
    Boolean isMoving = false;
    long tickFrame = 0;
    int frame = 0;
    float posX = 0;
    float posY = 0;
    Boolean horizontalFlip = false;

    // Texture
    Texture texSprite;

    public Character(Core core, String spritePath){
        this.core = core;

        texSprite = new Texture(Gdx.files.internal(spritePath));
    }

    public void setPos(float x, float y){
        posX = x;
        posY = y;
    }

    public void setHorizontalFlip(Boolean flip){
        horizontalFlip = flip;
    }

    public float getX(){
        return posX;
    }

    public float getY(){
        return posY;
    }

    public void update(float delta){
        if (isMoving){
            if (System.currentTimeMillis() > tickFrame){
                tickFrame = System.currentTimeMillis()+FRAME_INTERVAL;
                frame++;
                frame %= FRAME_COUNT;
            }
        }
        else frame = 0;
    }

    public void render(float delta){
        update(delta);

        core.batch.draw(texSprite, posX, posY, 32, 32, frame*32, 0, 32, 32, horizontalFlip, false);
    }

    public void dispose(){
        texSprite.dispose();
    }
}
