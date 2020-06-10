package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.geom.Point2D;

public class Background {

    // Core instance
    Core game;

    // Texture Pack
    Texture texBackground;
    Texture texTerrain;

    // Texture regions
    TextureRegion texCloud;
    TextureRegion texBgCloud;
    TextureRegion texBgMountain;
    TextureRegion texBgTop;
    TextureRegion texBgBlack;

    TextureRegion texTile1;
    TextureRegion texTile2;

    // Clouds
    final int MAXCLOUDS = 5;
    float[] cloudPositionX = new float[MAXCLOUDS];
    float[] cloudPositionY = new float[MAXCLOUDS];

    public Background(Core game){
        this.game = game;

        texBackground = new Texture(Gdx.files.internal("background.png"));
        texTerrain = new Texture(Gdx.files.internal("terrain.png"));

        texCloud = new TextureRegion(texBackground, 0, 16, 16, 16);
        texBgCloud = new TextureRegion(texBackground, 16, 0, 160, 32);
        texBgMountain = new TextureRegion(texBackground, 16, 32, 160, 48);
        texBgTop = new TextureRegion(texBackground, 0, 0, 16, 16);
        texBgBlack = new TextureRegion(texBackground,0, 32, 16, 16);

        texTile1 = new TextureRegion(texTerrain, 16, 0, 16, 16);
        texTile2 = new TextureRegion(texTerrain, 16, 16, 16, 16);

        // Setting initial cloud position
        for(int i = 0;i < MAXCLOUDS;i++){
            cloudPositionX[i] = game.rand.getIntRand(-16, (int)game.width);
            cloudPositionY[i] = game.rand.getIntRand(100,(int)game.height-64);
        }
    }

    public void loop(float delta){

    }

    public void render(float x){
        // Drawing top effect
        int cnt1 = ((int)game.width-1)/16+1;
        for(int i = 0;i < cnt1;i++){
            game.batch.draw(texBgTop, 16*i, game.height-48);
        }
        game.batch.draw(texBgBlack, 0, game.height-32, game.width, 32);

        // Drawing background clouds
        int cnt3 = ((int)game.width-1)/160+1;
        float offset3 = Utils.fixFloat((x/3f)%160);
        for(int i = 0;i < cnt3+1;i++){
            game.batch.draw(texBgCloud, 160*i - offset3, 64);
        }

        // Drawing mountain
        int cnt2 = ((int)game.width-1)/160+1;
        float offset2 = Utils.fixFloat((x/2f)%160);

        for(int i = 0;i < cnt2+1;i++){
            game.batch.draw(texBgMountain, 160*i - offset2, 16);
        }

        // Drawing terrain
        int cnt4 = ((int)game.width-1)/16+1;
        float offset4 = Utils.fixFloat(x%16);
        for(int i = 0;i < cnt4+1;i++){
            game.batch.draw(texTile1, 16*i-offset4, 16);
            game.batch.draw(texTile2, 16*i-offset4, 0);
        }

        // Drawing clouds
        for(int i = 0;i < MAXCLOUDS;i++){
            game.batch.draw(texCloud, cloudPositionX[i], cloudPositionY[i]);
        }
    }

    public void dispose(){
        texBackground.dispose();
        texTerrain.dispose();
    }
}
