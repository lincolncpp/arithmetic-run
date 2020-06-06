package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

    // Core instance
    Core game;

    // Texture Pack
    Texture texPack;

    // Texture regions
    TextureRegion texCloud;
    TextureRegion texBgCloud;
    TextureRegion texBgMountain;
    TextureRegion texBgTop;
    TextureRegion texBgBlack;

    public Background(Core game){
        this.game = game;

        texPack = new Texture(Gdx.files.internal("background.png"));

        texCloud = new TextureRegion(texPack, 0, 16, 16, 16);
        texBgCloud = new TextureRegion(texPack, 16, 0, 160, 32);
        texBgMountain = new TextureRegion(texPack, 16, 32, 160, 48);
        texBgTop = new TextureRegion(texPack, 0, 0, 16, 16);
        texBgBlack = new TextureRegion(texPack,0, 32, 16, 16);
    }

    public void render(float x){
        // Drawing top effect
        int cnt1 = ((int)game.width-1)/16+1;
        for(int i = 0;i < cnt1;i++){
            game.batch.draw(texBgTop, 16*i, game.height-48);
        }
        game.batch.draw(texBgBlack, 0, game.height-32, game.width, 32);

        // Drawing mountain
        int cnt2 = ((int)game.width-1)/160+1;
        float offset2 = x%160;
        for(int i = 0;i < cnt2+1;i++){
            game.batch.draw(texBgMountain, 160*i - offset2, 0);
        }

        // Drawing background clouds
        int cnt3 = ((int)game.width-1)/160+1;
        float offset3 = (x/2f)%160;
        for(int i = 0;i < cnt3+1;i++){
            game.batch.draw(texBgCloud, 160*i - offset3, 48);
        }
    }

    public void dispose(){
        texPack.dispose();
    }
}
