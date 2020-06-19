package com.usp.corrida.utils;

import com.badlogic.gdx.math.Vector2;
import com.usp.corrida.Core;

/**
 * Funções diversas
 */
public class Utils {

    public static final float PI = 3.14159265359f;

    /**
     * Corrige a precisão do float para 1 casa decimal
     * @param f número decimal da forma x.xxxxxxx
     * @return número decimal da forma x.x
     */
    public static float fixFloat(float f){
        return (int)(f*10)/10f;
    }

    /**
     * @param core Instancia do jogo
     * @param x Coordenada x do ponto clicado
     * @param y Coordenada y do ponto clicado
     * @return Ponto coordenado corrigido, origem no canto inferior esquerdo
     */
    public static Vector2 fixTouchPosition(Core core, int x, int y){
        Vector2 res = new Vector2(x, (int)core.height*2-y);
        res.y /= 2;
        res.x /= 2;
        return res;
    }
}
