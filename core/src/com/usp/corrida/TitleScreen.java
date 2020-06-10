package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TitleScreen extends ScreenAdapter {

    // Core instance
    Core game;

    // Screen variables
    float pos_x = 0;

    public TitleScreen(Core game){
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
                game.setScreen(new GameScreen(game));
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
        pos_x += delta*35f;
        game.background.render(pos_x);
    }

}
