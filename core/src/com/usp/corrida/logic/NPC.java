package com.usp.corrida.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;

/**
 * Classe responsável pela renderização dos NPCs
 */
public class NPC extends Character{

    /**
     * @param core Instancia do core do jogo
     * @param spriteID Identificador do sprite
     */
    public NPC(Core core, int spriteID) {
        super(core, spriteID);
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar tudo antes da renderização
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    @Override
    public void update(float delta, float offsetX){
        super.update(delta, offsetX);

    }

    /**
     * Renderiza o sprite do personagem + balão
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     * @param offsetX Deslocamento da coordenada x do cenário
     */
    @Override
    public void render(float delta, float offsetX){
        super.render(delta, offsetX);

        core.batch.setColor(1, 1, 1, 0.8f);
        core.batch.draw(core.res.texTextbox, getX()+10 -offsetX, getY()+20);
        core.batch.setColor(1, 1, 1, 1);
    }
}
