package com.usp.corrida.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Armazenamento interno
 */
public class Save {
    private final Preferences pref;
    private long highScore;

    public Save() {
        pref = Gdx.app.getPreferences("corridaritmetica");
        highScore = pref.getLong("highscore", 0);
    }

    /**
     * Define um novo valor para 'highscore' e descarrega na memória do dispositivo
     */
    public void setHighScore(long highScore) {
        this.highScore = highScore;
        pref.putLong("highscore", highScore);
        pref.flush();
    }

    public long getHighScore() {
        return highScore;
    }
}
