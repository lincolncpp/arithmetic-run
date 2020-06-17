package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;
import com.usp.corrida.screens.GameScreen;

/**
 * Classe destinada à renderização da tela de título
 */
public class TitleScreen extends ScreenAdapter {

    // Core instance
    Core core;

    // Texture
    Texture texHelpButton;

    /**
     * @param core Instancia do core do jogo
     */
    public TitleScreen(Core core){
        this.core = core;

        texHelpButton = new Texture(Gdx.files.internal("help.png"));
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

        // Draw game in background
        core.game.render(delta, 0);

        // Draw record text
        core.res.font.draw(core.batch, "RECORDE: 0", 10, core.height-11);

        // Draw "Touch to play"
        float multiplier = ((float)Math.sin((double)System.currentTimeMillis()/(double)300)+1)/2f;
        core.batch.setColor(1f, 1f, 1f, 0.3f+0.3f*multiplier);
        core.batch.draw(core.res.texBlack, 0, core.height/2-15, core.width, 30);
        core.batch.setColor(1, 1, 1, 1);
        core.res.font.draw(core.batch, "TOQUE PARA JOGAR!", 0, core.height/2+core.res.font.getCapHeight()/2, core.width, 1, true);

        // Draw help button
        core.batch.draw(texHelpButton, core.width-16-8, core.height-16-8);
    }

    /**
     * Descarrega todos os recursos
     */
    @Override
    public void dispose(){
        texHelpButton.dispose();
    }

}
