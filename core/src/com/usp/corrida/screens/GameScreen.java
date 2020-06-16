package com.usp.corrida.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.usp.corrida.Core;

public class GameScreen extends ScreenAdapter {

    // Core instance
    Core core;

    float x = 0;

    public GameScreen(Core core){
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

        x += delta*30;

        // Draw background
        core.game.render(delta, x);

        core.font.draw(core.batch, "PONTOS: 0", 10, core.height-11);
    }
}
