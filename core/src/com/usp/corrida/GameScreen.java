package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

    // Core instance
    Core game;

    float x = 0;

    public GameScreen(Core game){
        this.game = game;
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
        game.background.render(x);

        game.font.draw(game.batch, "PONTOS: 0", 10, game.height-11);
    }
}
