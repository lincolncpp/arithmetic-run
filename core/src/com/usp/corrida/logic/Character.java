package com.usp.corrida.logic;

import com.usp.corrida.Core;

/**
 * Classe responsável pela renderização dos personagens
 */
public class Character {

    // Configuration constants
    public static final int FRAME_INTERVAL = 150;

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

    String text = "";

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
     * Define a mensagem no balão de fala do personagem
     */
    public void setText(String text){
        this.text = text;
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

        if (text.length() > 0){
            core.batch.setColor(1, 1, 1, 0.8f);
            core.batch.draw(core.res.texTextbox, getX()+10 -offsetX, getY()+20);
            core.batch.setColor(1, 1, 1, 1);

            core.res.font.setColor(0, 0, 0, 1);
            core.res.font.draw(core.batch, text, getX()+10-offsetX, getY()+20+9+23f/2+core.res.font.getCapHeight()/2, 64, 1, false);
            core.res.font.setColor(1, 1, 1, 1);
        }
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){


    }
}
