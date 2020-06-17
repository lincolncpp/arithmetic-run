package com.usp.corrida.utils;

import java.util.Calendar;

/**
 * Cuida das funções aleatórias
 */
public class Random {
    private final long p = 2147483648L;
    private final long m = 843314861L;
    private final long a = 453816693L;

    private long xi = 0;

    public Random(long seed) {
        xi = seed;
    }

    public Random() {
        xi = Calendar.getInstance().getTimeInMillis() % p;
    }

    public double getRand() {
        xi = (a+m*xi) % p;
        return (double)xi/p;
    }

    public int getIntRand(int max) {
        return (int)(getRand()*max);
    }

    public int getIntRand(int min, int max){
        return min+getIntRand(max-min+1);
    }
}
