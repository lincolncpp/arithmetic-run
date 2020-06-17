package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;

public class NPC extends Character{

    // Arithmetic expression level
    int nivel;

    // Texture
    Texture texTextbox;

    /**
     * @param core Instancia do core do jogo
     * @param spriteID Identificador do sprite
     * @param nivel Nivel da expressão aritmética a ser gerada
     */
    public NPC(Core core, int spriteID, int nivel) {
        super(core, spriteID);

        this.nivel = nivel;

        texTextbox = new Texture(Gdx.files.internal("textbox.png"));
    }

    /**
     * Renderiza o sprite do personagem + balão
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    @Override
    public void render(float delta){
        super.render(delta);

        core.batch.draw(texTextbox, getX()+10, getY()+20);
    }
}
