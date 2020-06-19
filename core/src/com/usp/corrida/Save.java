package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
    private Preferences pref;
    private long highScore;

    public Save() {
        pref = Gdx.app.getPreferences("corridaritmetica");
        highScore = pref.getLong("highscore", 0);
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
        pref.putLong("highscore", highScore);
        pref.flush();
    }
    public long getHighScore() {
        return highScore;
    }
}
