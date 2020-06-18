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
     * @param spriteID Identificador do sprite
     */
    public void setSprite(int spriteID){
        this.frame = 0;
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
     * Define a posição X do personagem
     */
    public void setX(float x){
        posX = x;
    }

    /**
     * Define a posição Y do personagem
     */
    public void setY(float y){
        posY = y;
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
     * Essa função é chamada antes da função render. É utilizada para atualizar os frames de movimento
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void update(float delta, float offsetX){
        if (isMoving){
            if (System.currentTimeMillis() > tickFrame){
                tickFrame = System.currentTimeMillis()+FRAME_INTERVAL;
                frame++;
                frame %= core.res.SPRITE_FRAMES[spriteID];
            }
        }
        else frame = 0;
    }

    /**
     * Renderiza o sprite do personagem
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void render(float delta, float offsetX){
        update(delta, offsetX);

        core.batch.draw(core.res.texSprite[spriteID], posX-offsetX, posY, core.res.SPRITE_WIDTH[spriteID], core.res.SPRITE_HEIGHT[spriteID], frame*core.res.SPRITE_WIDTH[spriteID], 0, core.res.SPRITE_WIDTH[spriteID], core.res.SPRITE_HEIGHT[spriteID], horizontalFlip, false);
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){


    }
}
