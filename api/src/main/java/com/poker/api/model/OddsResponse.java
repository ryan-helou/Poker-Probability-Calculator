package com.poker.api.model;

public class OddsResponse {

    public float win, tie, lose;

    public OddsResponse(float win, float tie, float lose) {
        this.win = win;
        this.tie = tie;
        this.lose = lose;
    }
}
