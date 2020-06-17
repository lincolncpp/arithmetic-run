package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;
import com.usp.corrida.Resources;

import javax.swing.text.StyledEditorKit;

/**
 * Classe responsável pela renderização dos personagens
 */
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
    int spriteID = 0;

    /**
     * @param core Instancia do core do jogo
     * @param spriteID Identificador do sprite
     */
    public Character(Core core, int spriteID){
        this.core = core;
        this.spriteID = spriteID;


    }

    /**
     * Define a posição coordenada do personagem na tela
     */
    public void setPos(float x, float y){
        posX = x;
        posY = y;
    }

    /**
     * Define o flip horizontal do sprite
     */
    public void setHorizontalFlip(Boolean flip){
        horizontalFlip = flip;
    }

    /**
     * Define se o personagem está em movimento ou não
     */
    public void setIsMoving(Boolean moving){
        isMoving = moving;
    }

    /**
     * @return Posição X
     */
    public float getX(){
        return posX;
    }

    /**
     * @return Posição Y
     */
    public float getY(){
        return posY;
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar os frames de movimento
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
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

    /**
     * Renderiza o sprite do personagem
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    public void render(float delta){
        update(delta);

        core.batch.draw(core.res.texSprite[spriteID], posX, posY, 32, 32, frame*32, 0, 32, 32, horizontalFlip, false);
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){


    }
}
