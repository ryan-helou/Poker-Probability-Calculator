package com.poker.api.model;

public class OddsRequest {

    public int value1;      // 2..14
    public String suit1;    // "Spades"|"Hearts"|"Clubs"|"Diamonds"
    public int value2;      // 2..14
    public String suit2;
    public int players;     // >=2
    public int iterations;  // e.g. 50000
}
