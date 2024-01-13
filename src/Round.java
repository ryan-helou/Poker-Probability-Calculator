import java.sql.SQLOutput;
import java.util.ArrayList;

public class Round {
    public static ArrayList<Player> playerArrayList = new ArrayList<>();
    public static int[][] combosArray = new int[21][5];
    static Player tempPlayer;

    public static void dealCardsToPlayers() {
        for (int i = 0; i < playerArrayList.size(); i++) {
            playerArrayList.get(i).holeCard1 = Deck.dealCard();
            playerArrayList.get(i).holeCard2 = Deck.dealCard();
        }
    }

    public static void dealCommonCards() {
        PokerUtility.commonCards.clear();
        for (int i = 0; i < 5; i++) {
            PokerUtility.commonCards.add(Deck.dealCard());
        }
    }

    public static void createPlayers(int players) {
        playerArrayList.clear();
        for (int i = 0; i < players; i++) {
            playerArrayList.add(new Player());
        }
    }

    public static void startRound(int players) {
        Deck.makeDeck();
        createPlayers(players);
        dealCardsToPlayers();
        dealCommonCards();
        showPlayersHands();
        showCommonCards();
        int bestPlayer = 0;
        double scoreBestPlayer = 0;
        boolean tie = false;
        PokerUtility.buildCombosArray();
        for (int i = 0; i < players; i++) {
            double tempScore = PokerUtility.getScoreForPlayer(playerArrayList.get(i));
            if (tempScore == scoreBestPlayer) {
                tie = true;
            }
            if (tempScore > scoreBestPlayer) {
                scoreBestPlayer = tempScore;
                bestPlayer = i + 1;
                tie = false;
            }
        }
        if (tie) {
            System.out.println("Tied players are: ");
            for (int i = 0; i < players; i++) {
                double tempScore = PokerUtility.getScoreForPlayer(playerArrayList.get(i));
                if (tempScore == scoreBestPlayer) {
                    System.out.print(i + 1 + " ");
                }
            }
        }

        if (!tie) {
            System.out.println("Player " + bestPlayer + " wins");
        }
    }


    public static void showPlayersHands() {
        for (int i = 0; i < playerArrayList.size(); i++) {
            System.out.print("Player " + (i + 1) + ": " + playerArrayList.get(i).holeCard1 + " ");
            System.out.println(playerArrayList.get(i).holeCard2);
        }
    }

    public static void showCommonCards() {
        System.out.print("Common cards are: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(PokerUtility.commonCards.get(i) + " ");
        }
        System.out.println();

    }

    public static int suitToNumber(String suit) {
        if (suit.equalsIgnoreCase("Spades") || suit.equalsIgnoreCase("Spade")) {
            return 0;
        }
        if (suit.equalsIgnoreCase("Hearts") || suit.equalsIgnoreCase("Heart")) {
            return 1;
        }
        if (suit.equalsIgnoreCase("Clubs") || suit.equalsIgnoreCase("Club")) {
            return 2;
        }
        if (suit.equalsIgnoreCase("Diamonds") || suit.equalsIgnoreCase("Diamond")) {
            return 3;
        } else {
            System.out.println("Not Valid Suit");
            return 100;
        }
    }

    public static int cardToIndex(int value, int suit) {
        int index = 0;
        index = 13 * suit + value - 2;
        return index;
    }

    public static void simulateMultipleRounds(int value1, String suit1, int value2,
                                              String suit2, int players, int iterations) {
        if(value1 == value2 && suit1.equalsIgnoreCase(suit2)){
            System.out.println("SAME CARDS");
            return;
        }
        else if(value1 < 2 || value1 > 14 || value2 < 2 || value2 > 14){
            System.out.println("INVALID CARDS");
            return;
        }

        int tieCounter = 0;
        int winCounter = 0;
        int lossCounter = 0;
        Card tempCard1 = new Card(value1, Card.suitsArray[suitToNumber(suit1)]);
        Card tempCard2 = new Card(value2, Card.suitsArray[suitToNumber(suit2)]);
        System.out.println("The chances of the outcomes with the cards " + tempCard1 + " and " + tempCard2 + " as "+ players + " players is the following: ");
        for(int z = 0; z < iterations; z++){

            Deck.makeDeck();
            createPlayers(players);
            int index1 = cardToIndex(value1, suitToNumber(suit1));
            int index2 = cardToIndex(value2, suitToNumber(suit2));
            Deck.deckOfCards.remove(index1);
            Deck.deckOfCards.remove(index2);
            dealCardsToPlayers();
            playerArrayList.get(0).holeCard1 = tempCard1;
            playerArrayList.get(0).holeCard2 = tempCard2;
            dealCommonCards();
            double bestScore = 0;
            PokerUtility.buildCombosArray();
            double playerOneScore = PokerUtility.getScoreForPlayer(playerArrayList.get(0));
            for (int j = 1; j < players; j++) {
                double tempScore = PokerUtility.getScoreForPlayer(playerArrayList.get(j));
                if (tempScore > bestScore) {
                    bestScore = tempScore;
                }
            }
            if (playerOneScore > bestScore) {
                winCounter++;
            }
            if (playerOneScore < bestScore) {
                lossCounter++;
            }
            if (playerOneScore == bestScore) {
                tieCounter++;
            }

        }
        float winPercentage = (winCounter)*100f/(iterations);
        float lossPercentage = (lossCounter)*100f/(iterations);
        float tiePercentage = (tieCounter)*100f/(iterations);

        System.out.println("Wins: " +winPercentage+ "%");
        System.out.println("Losses: "+lossPercentage + "%");
        System.out.println("Ties: "+tiePercentage + "%");

    }
}
