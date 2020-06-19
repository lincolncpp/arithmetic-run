package com.usp.corrida.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.usp.corrida.Core;
import com.usp.corrida.utils.Utils;

/**
 * Controla tudo relacionado ao personagem: renderização, posição, animação, toque e caixa de diálogo.
 */
public class Character {
    public static final long BASE_FRAME_INTERVAL = 120;

    private final Core core;

    private Boolean isMoving = false;
    private long tickFrame = 0;
    private long frameInterval = 120;
    private int frame = 0;

    private final Vector2 position = new Vector2(0, 0);
    private Boolean horizontalFlip = false;
    private int sprite = 0;

    private String text = "";
    private int value = 0;

    public Character(Core core, int sprite){
        this.core = core;
        this.sprite = sprite;
    }

    /**
     * @param sprite Identificador do sprite
     */
    public void setSprite(int sprite){
        this.frame = 0;
        this.sprite = sprite;
    }

    /**
     * @return Identificador do sprite
     */
    public int getSprite(){
        return sprite;
    }

    /**
     * @param frame Quadro de animação
     */
    public void setFrame(int frame){
        this.frame = frame;
    }

    /**
     * @return Quadro de animação
     */
    public int getFrame(){
        return frame;
    }

    /**
     * @param frameInterval Intervalo em ms entre quadros da animação do sprite
     */
    public void setFrameInterval(long frameInterval){
        this.frameInterval = frameInterval;
    }

    /**
     * @return Intervalo em ms entre quadros da animação do sprite
     */
    public long getFrameInterval(){
        return frameInterval;
    }

    /**
     * @param text Texto associado ao personagem
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * @return Texto associado ao personagem
     */
    public String getText(){
        return text;
    }

    /**
     * @param value Valor associado ao personagem
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * @return Valor associado ao personagem
     */
    public int getValue(){
        return value;
    }

    /**
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public void setPos(float x, float y){
        position.set(x, y);
    }

    /**
     * @return Posição coordenada do personagem
     */
    public Vector2 getPos(){
        return position;
    }

    /**
     *
     * @param x Coordenada x
     */
    public void setX(float x){
        position.x = x;
    }

    /**
     * @return Coordenada x
     */
    public float getX(){
        return position.x;
    }

    /**
     * @param y Coordenada y
     */
    public void setY(float y){
        position.y = y;
    }

    /**
     * @return Coordenada y
     */
    public float getY(){
        return position.y;
    }

    /**
     * @param flip Flip horizontal do sprite
     */
    public void setHorizontalFlip(Boolean flip){
        horizontalFlip = flip;
    }

    /**
     * @return Flip horizontal do sprite
     */
    public Boolean getHorizontalFlip(){
        return horizontalFlip;
    }

    /**
     * @param moving Sprite em animação ou não
     */
    public void setIsMoving(Boolean moving){
        isMoving = moving;
    }

    /**
     * @return Sprite em animação ou não
     */
    public Boolean getIsMoving(){
        return isMoving;
    }

    /**
     * Faz o uso do Intersector para checar se um ponto está dentro de um determinado polígono
     * @param x Coordenada x do ponto clicado
     * @param y Coordenada y do ponto clicado
     * @param offsetX Deslocamento da coordenada x
     * @return Retorna se foi tocado sobre o personagem ou não
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
        rect.add(new Vector2(getX()+core.res.SPRITE_WIDTH[sprite], getY()));

        return Intersector.isPointInPolygon(rect, point);
    }

    /**
     * Chamada logo no início da função render. É utilizada para atualizar os quadros de animação
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x
     */
    private void update(float delta, float offsetX){
        if (isMoving){
            if (System.currentTimeMillis() > tickFrame){
                tickFrame = System.currentTimeMillis()+frameInterval;
                frame++;
                frame %= core.res.SPRITE_FRAMES[sprite];
            }
        }
    }

    /**
     * Renderiza o sprite do personagem
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x
     */
    public void render(float delta, float offsetX){
        update(delta, offsetX);

        // Fixing float values
        offsetX = Utils.fixFloat(offsetX);
        Vector2 fixedPosition = new Vector2(getX(), getY());
        fixedPosition.x = Utils.fixFloat(fixedPosition.x);
        fixedPosition.y = Utils.fixFloat(fixedPosition.y);

        // Drawing sprite
        core.batch.draw(core.res.texSprite[sprite], fixedPosition.x-offsetX, fixedPosition.y, core.res.SPRITE_WIDTH[sprite], core.res.SPRITE_HEIGHT[sprite], frame*core.res.SPRITE_WIDTH[sprite], 0, core.res.SPRITE_WIDTH[sprite], core.res.SPRITE_HEIGHT[sprite], horizontalFlip, false);

        // Drawing textbox
        if (text.length() > 0){
            core.batch.setColor(1, 1, 1, 0.8f);
            core.batch.draw(core.res.texTextbox, fixedPosition.x+10 -offsetX, fixedPosition.y+20);
            core.batch.setColor(1, 1, 1, 1);

            core.res.font20.setColor(0, 0, 0, 1);
            core.res.font20.draw(core.batch, text, fixedPosition.x+10-offsetX, fixedPosition.y+20+9+23f/2+core.res.font20.getCapHeight()/2, 64, 1, false);
            core.res.font20.setColor(1, 1, 1, 1);
        }
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){


    }
}
