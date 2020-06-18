package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;
import com.usp.corrida.logic.Game;

/**
 * Classe destinada à renderização da tela de jogo
 */
public class GameScreen extends ScreenAdapter {

    // Core instance
    Core core;

    // Texture
    Texture texLife;

    // Screen offset
    float x = 0;

    /**
     * @param core Instancia do core do jogo
     */
    public GameScreen(Core core){
        this.core = core;

        texLife = new Texture(Gdx.files.internal("life.png"));
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

        // Set character moving
        core.game.charMain.setIsMoving(true);
        core.game.charMain.setText("1?");
    }

    /**
     * Essa função é chamada quando a tela acaba de ser escondida
     */
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);

        // Set character moving
        core.game.charMain.setIsMoving(false);
        core.game.charMain.setText("");
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

        // Drawing life
        for(int i = 0;i < 3;i++){
            core.batch.draw(texLife,10+20*i, core.height-32/2f-16/2f, (i+1>core.game.life)?16:0, 0, 16, 16);
        }

        // Drawing score
        core.res.font.draw(core.batch, "PONTOS: 0", 10+20*2+16+10, core.height-32/2f+core.res.font.getCapHeight()/2f);
    }

    /**
     * Descarrega todos os recursos
     */
    @Override
    public void dispose(){
        texLife.dispose();
    }
}
