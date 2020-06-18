package com.usp.corrida.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.usp.corrida.Core;
import com.usp.corrida.utils.Utils;

/**
 * Classe responsável pela renderização dos personagens
 */
public class Character {

    // Core instance
    Core core;

    // Character variables
    Boolean isMoving = false;
    long tickFrame = 0;
    long frameInterval = 120;
    int frame = 0;

    Vector2 position;
    Boolean horizontalFlip = false;
    int spriteID = 0;

    String text = "";
    int value = 0;

    /**
     * @param core Instancia do core do jogo
     * @param spriteID Identificador do sprite
     */
    public Character(Core core, int spriteID){
        this.core = core;
        this.spriteID = spriteID;

        position = new Vector2(0, 0);
    }

    /**
     * @param spriteID Identificador do sprite
     */
    public void setSprite(int spriteID){
        this.frame = 0;
        this.spriteID = spriteID;
    }

    /**
     * @param frameInterval intervalo entre frames da animação do sprite
     */
    public void setFrameInterval(long frameInterval){
        this.frameInterval = frameInterval;
    }

    /**
     * Define a mensagem no balão de fala do personagem
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * Define o valor associado ao personagem
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * @return valor associado ao personagem
     */
    public int getValue(){
        return value;
    }

    /**
     * Define a posição coordenada do personagem na tela
     */
    public void setPos(float x, float y){
        position.set(x, y);
    }

    /**
     * Define a posição X do personagem
     */
    public void setX(float x){
        position.x = x;
    }

    /**
     * Define a posição Y do personagem
     */
    public void setY(float y){
        position.y = y;
    }

    /**
     * @return Posição X
     */
    public float getX(){
        return position.x;
    }

    /**
     * @return Posição Y
     */
    public float getY(){
        return position.y;
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
     *
     * @param x coordenada x do ponto clicado
     * @param y coordenada y do ponto clicado
     * @param offsetX Deslocamento da coordenada x do cenário
     * @return Retorna se o personagem foi tocado ou não
     */
    public Boolean isTouched(int x, int y, float offsetX){
        float textX = getX()+10;
        float textY = getY()+20;
        float textWidth = 64;
        float textHeight = 32;

        Vector2 point = new Vector2(x+offsetX, y);

        Array<Vector2> rect = new Array<Vector2>();
        rect.add(new Vector2(getX(), getY()));
        rect.add(new Vector2(textX, textY+textHeight));
        rect.add(new Vector2(textX+textWidth, textY+textHeight));
        rect.add(new Vector2(textX+textWidth, textY));
        rect.add(new Vector2(getX()+core.res.SPRITE_WIDTH[spriteID], getY()));

        return Intersector.isPointInPolygon(rect, point);
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar os frames de movimento
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    public void update(float delta, float offsetX){
        if (isMoving){
            if (System.currentTimeMillis() > tickFrame){
                tickFrame = System.currentTimeMillis()+frameInterval;
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

        offsetX = Utils.fixFloat(offsetX);

        Vector2 fixedPosition = new Vector2(getX(), getY());
        fixedPosition.x = Utils.fixFloat(fixedPosition.x);
        fixedPosition.y = Utils.fixFloat(fixedPosition.y);

        core.batch.draw(core.res.texSprite[spriteID], fixedPosition.x-offsetX, fixedPosition.y, core.res.SPRITE_WIDTH[spriteID], core.res.SPRITE_HEIGHT[spriteID], frame*core.res.SPRITE_WIDTH[spriteID], 0, core.res.SPRITE_WIDTH[spriteID], core.res.SPRITE_HEIGHT[spriteID], horizontalFlip, false);

        if (text.length() > 0){
            core.batch.setColor(1, 1, 1, 0.8f);
            core.batch.draw(core.res.texTextbox, fixedPosition.x+10 -offsetX, fixedPosition.y+20);
            core.batch.setColor(1, 1, 1, 1);

            core.res.font.setColor(0, 0, 0, 1);
            core.res.font.draw(core.batch, text, fixedPosition.x+10-offsetX, fixedPosition.y+20+9+23f/2+core.res.font.getCapHeight()/2, 64, 1, false);
            core.res.font.setColor(1, 1, 1, 1);
        }
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){


    }
}
