package com.usp.corrida;

import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter {
    @Override
    public boolean keyTyped (char key) {
        System.out.println(key);
        return true;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        System.out.println(x+" "+y);
        return true;
    }
}
