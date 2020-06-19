package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.usp.corrida.Core;
import com.usp.corrida.logic.Character;

/**
 * É a tela que renderiza a parte inicial do jogo. Para alternar entre telas, use a função setScreen da classe Game, passando como parâmetro o objeto da tela.
 */
public class TitleScreen extends ScreenAdapter {
    private final Core core;

    private Texture texHelpButton;

    public TitleScreen(Core core){
        this.core = core;

        texHelpButton = new Texture(Gdx.files.internal("help.png"));

        resetScreen();
    }

    /**
     * Volta ao estado zero
     */
    private void resetScreen(){
        // Resetting character
        core.charPlayer.setIsMoving(false);
        core.charPlayer.setPos(32, 32);
        core.charPlayer.setText("");
        core.charPlayer.setFrameInterval(Character.BASE_FRAME_INTERVAL);
        core.charPlayer.setFrame(0);

        // Resetting background
        core.background.resetBackground();
    }

    /**
     * Chamada quando esse objeto é definido pela função setScreen da classe Game
     */
    @Override
    public void show(){
        resetScreen();

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
     * Chamada quando esse objeto não for mais a tela atual, definida pela função setScreen da classe Game
     */
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Chamada logo no início da função render. É utilizada para atualizar tudo antes da renderização
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    private void update(float delta){

    }

    /**
     * Renderiza a tela de título
     * @param delta Variação de tempo entre a chamada atual e a última chamada
     */
    @Override
    public void render (float delta) {
        update(delta);

        // Draw background
        core.background.render(delta, 0);

        // Draw player
        core.charPlayer.render(delta, 0);

        // Draw record text
        core.res.font20.draw(core.batch, "RECORDE: "+core.save.getHighScore(), 10, core.height-32/2f+core.res.font20.getCapHeight()/2f);

        // Draw "Touch to play"
        float multiplier = ((float)Math.sin((double)System.currentTimeMillis()/(double)300)+1)/2f;
        core.batch.setColor(1f, 1f, 1f, 0.3f+0.3f*multiplier);
        core.batch.draw(core.res.texBlack, 0, core.height/2-15, core.width, 30);
        core.batch.setColor(1, 1, 1, 1);
        core.res.font20.draw(core.batch, "TOQUE PARA JOGAR!", 0, core.height/2+core.res.font20.getCapHeight()/2, core.width, 1, false);

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
