package com.poker.engine;

public class OddsResult {

    public final float win;  // percent 0..100
    public final float tie;
    public final float lose;

    public OddsResult(float win, float tie, float lose) {
        this.win = win;
        this.tie = tie;
        this.lose = lose;
    }
}
