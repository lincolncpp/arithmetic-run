package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.usp.corrida.Core;
import com.usp.corrida.utils.Utils;

/**
 * Classe responsável pela renderização do cenário
 */
public class Game {

    // Core instance
    Core core;

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
    public static final int MAXCLOUDS = 5;
    float[] cloudPositionX = new float[MAXCLOUDS];
    float[] cloudPositionY = new float[MAXCLOUDS];

    // Character
    public Character charMain;

    // NPC
    public NPC npc1;

    /**
     * @param core Instancia do core do jogo
     */
    public Game(Core core){
        this.core = core;

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
            cloudPositionX[i] = core.rand.getIntRand(-16, (int)core.width);
            cloudPositionY[i] = core.rand.getIntRand(100,(int)core.height-64);
        }

        charMain = new Character(core, 1);
        charMain.setPos(32, 32);

        npc1 = new NPC(core, 1, 1);
        npc1.setPos(32*10, 32);
        npc1.setHorizontalFlip(true);
    }

    /**
     * Renderiza todo o cenário do jogo
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param x Deslocamento da coordenada x do cenário
     */
    public void render(float delta, float x){

        // Drawing top effect
        int cnt1 = ((int)core.width-1)/16+1;
        for(int i = 0;i < cnt1;i++){
            core.batch.draw(texBgTop, 16*i, core.height-48);
        }
        core.batch.draw(texBgBlack, 0, core.height-32, core.width, 32);

        // Drawing background clouds
        int cnt3 = ((int)core.width-1)/160+1;
        float offset3 = Utils.fixFloat((x/4f)%160);
        for(int i = 0;i < cnt3+1;i++){
            core.batch.draw(texBgCloud, 160*i - offset3, 64);
        }

        // Drawing mountain
        int cnt2 = ((int)core.width-1)/160+1;
        float offset2 = Utils.fixFloat((x/2f)%160);

        for(int i = 0;i < cnt2+1;i++){
            core.batch.draw(texBgMountain, 160*i - offset2, 16);
        }

        // Drawing terrain
        int cnt4 = ((int)core.width-1)/16+1;
        float offset4 = Utils.fixFloat(x%16);
        for(int i = 0;i < cnt4+1;i++){
            core.batch.draw(texTile1, 16*i-offset4, 16);
            core.batch.draw(texTile2, 16*i-offset4, 0);
        }

        // Drawing clouds
        for(int i = 0;i < MAXCLOUDS;i++){
            core.batch.draw(texCloud, cloudPositionX[i], cloudPositionY[i]);
        }

        charMain.render(delta);
        npc1.render(delta);
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){
        texBackground.dispose();
        texTerrain.dispose();
    }
}
