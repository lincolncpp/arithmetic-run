package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

public class TitleScreen extends ScreenAdapter {

    // Core instance
    Core core;

    public TitleScreen(Core core){
        this.core = core;
    }

    @Override
    public void dispose(){
    }

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

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public void loop(float delta){

    }

    @Override
    public void render (float delta) {
        loop(delta);

        // Draw background
        core.game.render(delta, 0);

        core.font.draw(core.batch, "RECORDE: 9.632", 10, core.height-11);
    }

}
