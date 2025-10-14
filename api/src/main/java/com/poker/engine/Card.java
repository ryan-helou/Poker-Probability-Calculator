package com.poker.engine;

import java.util.Random;

public class Card {

    String rank;
    int value;

    String suit;
    static String[] suitsArray = {"♠", "♥", "♣", "♦"};

    @Override
    public String toString() {
        return rank + suit;
    }

    public Card(int v, String s) {
        this.value = v;
        this.suit = s;

        switch (v) {
            case 11:
                this.rank = "J";
                break;

            case 12:
                this.rank = "Q";
                break;
            case 13:
                this.rank = "K";
                break;
            case 14:
                this.rank = "A";
                break;
            default:
                this.rank = Integer.toString(v);
                break;

        }

    }
}
