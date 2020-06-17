package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.usp.corrida.Core;
import com.usp.corrida.screens.GameScreen;

/**
 * Classe destinada à renderização da tela de título
 */
public class TitleScreen extends ScreenAdapter {

    // Core instance
    Core core;

    /**
     * @param core Instancia do core do jogo
     */
    public TitleScreen(Core core){
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
                core.setScreen(new GameScreen(core));
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
     * Renderiza a tela de título
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    @Override
    public void render (float delta) {
        update(delta);

        // Draw background
        core.game.render(delta, 0);

        core.res.font.draw(core.batch, "RECORDE: 9.632", 10, core.height-11);

//        core.font.draw(core.batch, "Toque para jogar!", )
        core.res.font.draw(core.batch, "Toque para jogar!", 0, core.height/2, core.width, 1, true);
    }

    /**
     * Descarrega todos os recursos
     */
    @Override
    public void dispose(){
    }

}
