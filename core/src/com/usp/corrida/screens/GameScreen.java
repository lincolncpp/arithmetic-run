package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.usp.corrida.Core;

/**
 * Classe destinada à renderização da tela de jogo
 */
public class GameScreen extends ScreenAdapter {

    // Core instance
    Core core;

    float x = 0;

    /**
     * @param core Instancia do core do jogo
     */
    public GameScreen(Core core){
        this.core = core;
    }

    /**
     * Essa função é chamada quando a tela acaba de ser exibida
     */
    @Override
    public void show(){
        // Input Processor
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                return true;
            }
        });
    }

    /**
     * Essa função é chamada quando a tela acaba de ser escondida
     */
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Essa função é chamada antes da função render. É utilizada para atualizar tudo antes da renderização
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    public void update(float delta){

    }

    /**
     * Renderiza a tela de jogo
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    @Override
    public void render (float delta) {
        update(delta);

        x += delta*30;

        // Draw background
        core.game.render(delta, x);

        core.font.draw(core.batch, "PONTOS: 0", 10, core.height-11);
    }

    /**
     * Descarrega todos os recursos
     */
    @Override
    public void dispose(){
    }
}
