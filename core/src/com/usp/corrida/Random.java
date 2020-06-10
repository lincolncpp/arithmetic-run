package com.usp.corrida;

import java.util.Calendar;

public class Random {
    private long p = 2147483648L;
    private long m = 843314861L;
    private long a = 453816693L;

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
