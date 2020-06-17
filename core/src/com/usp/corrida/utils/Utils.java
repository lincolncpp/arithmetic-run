package com.usp.corrida.utils;

/**
 * Funções diversas para auxiliar no desenvolvimento do programa
 */
public class Utils {

    /**
     * Corrige a precisão do float para 1 casa decimal
     * @param f número decimal da forma x.xxxxxxx
     * @return número decimal da forma x.x
     */
    public static float fixFloat(float f){
        return (int)(f*10)/10f;
    }
}
