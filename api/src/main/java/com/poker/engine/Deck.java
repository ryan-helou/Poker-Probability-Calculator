package com.poker.engine;
import java.util.ArrayList;
import java.util.Random;

public class Deck {

    static ArrayList <Card> deckOfCards = new ArrayList<>();

    public static void makeDeck(){
        deckOfCards.clear();
        for (int i = 0; i < 4; i++){
            for (int j = 2; j <= 14; j ++){
                deckOfCards.add(new Card(j , Card.suitsArray[i]));
            }
        }

    }

    public static int randomNumber (int max){
        Random random = new Random();
        int number = random.nextInt(max) + 1;
        return number;
    }
    public static Card dealCard(){
            int cardSpot = randomNumber(deckOfCards.size()-1);

            //System.out.print(deckOfCards.get(cardSpot-1));

            Card tempCard =  deckOfCards.get(cardSpot-1);

            deckOfCards.remove(cardSpot-1);

            return tempCard;

    }


    public static void texasHoldem (int players){

    }
}
