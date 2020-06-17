package com.usp.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Classe responsável pelo carregamento de recursos compartilhados
 */
public class Resources {

    public static final int MAX_SPRITES = 1;

    // Shared font
    public BitmapFont font;

    // Shared texture
    public Texture texBlack;

    // Sprites textures
    public Texture[] texSprite = new Texture[MAX_SPRITES];

    /**
     * Faz o carregamento de todos os recursos compartilhados
     */
    public Resources(){
        font = new BitmapFont(Gdx.files.internal("font/clacon.fnt"));

        texBlack = new Texture(Gdx.files.internal("black.png"));

        // Loading sprites
        for(int i = 0;i < MAX_SPRITES;i++){
            texSprite[i] = new Texture(Gdx.files.internal("sprites/"+i+".png"));
        }
    }

    /**
     * Descarrega todos os recursos
     */
    public void dispose(){
        font.dispose();
        texBlack.dispose();

        for(int i = 0;i < MAX_SPRITES;i++) {
            texSprite[i].dispose();
        }
    }
}
