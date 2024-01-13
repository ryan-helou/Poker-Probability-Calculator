import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class PokerUtility {

    public static ArrayList <Card> commonCards = new ArrayList<>();
    public static ArrayList <ArrayList<Card>> allCombos = new ArrayList<>();

    public static int[][] combosArray = new int[21][5];
    static Card tempHoleCard1;
    static Card tempHoleCard2;

    public static ArrayList<ArrayList<Card>> makeAllHandCombinations (Player p){
        ArrayList<Card> allCards = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            allCards.add(commonCards.get(i));
        }
        //System.out.println(allCards);
        allCards.add(p.holeCard1);
        allCards.add(p.holeCard2);
        //System.out.println(allCards);
        ArrayList<ArrayList<Card>> combinations = new ArrayList<ArrayList<Card>>();
        for(int i = 0; i < combosArray.length;i++){
            ArrayList<Card> arrayOfOneCombo = new ArrayList<>();
            for(int j = 0; j < 5; j ++){
                switch(combosArray[i][j]){
                    case 1:
                        arrayOfOneCombo.add(allCards.get(0));
                        break;
                    case 2:
                        arrayOfOneCombo.add(allCards.get(1));
                        break;
                    case 3:
                        arrayOfOneCombo.add(allCards.get(2));
                        break;
                    case 4:
                        arrayOfOneCombo.add(allCards.get(3));
                        break;
                    case 5:
                        arrayOfOneCombo.add(allCards.get(4));
                        break;
                    case 6:
                        arrayOfOneCombo.add(allCards.get(5));
                        break;
                    case 7:
                        arrayOfOneCombo.add(allCards.get(6));
                        break;

                }
            }
            combinations.add(arrayOfOneCombo);
        }
        sortAllArrays(combinations);
        return combinations;
    }



    public static void buildCombosArray() {
        combosArray[0] = new int[] {1,2,3,4,5};
        combosArray[1] = new int[] {1,2,3,4,6};
        combosArray[2] = new int[] {1,2,3,4,7};
        combosArray[3] = new int[] {1,2,3,5,6};
        combosArray[4] = new int[] {1,2,3,5,7};
        combosArray[5] = new int[] {1,2,3,6,7};
        combosArray[6] = new int[] {1,2,5,6,7};
        combosArray[7] = new int[] {1,2,4,5,6};
        combosArray[8] = new int[] {1,2,4,5,7};
        combosArray[9] = new int[] {1,2,4,6,7};
        combosArray[10] = new int[] {1,3,4,5,6};
        combosArray[11] = new int[] {1,3,4,5,7};
        combosArray[12] = new int[] {1,3,4,6,7};
        combosArray[13] = new int[] {1,3,5,6,7};
        combosArray[14] = new int[] {1,4,5,6,7};
        combosArray[15] = new int[] {2,3,4,5,6};
        combosArray[16] = new int[] {2,3,4,5,7};
        combosArray[17] = new int[] {2,3,4,6,7};
        combosArray[18] = new int[] {2,3,5,6,7};
        combosArray[19] = new int[] {2,4,5,6,7};
        combosArray[20] = new int[] {3,4,5,6,7};
    }

    public static void dealCardsToPlayers (){
        for(int i = 0; i < Round.playerArrayList.size(); i++){
            Round.playerArrayList.get(i).holeCard1 = Deck.dealCard();
            Round.playerArrayList.get(i).holeCard2 = Deck.dealCard();
        }
    }

    public static void dealCommonCards (){
        for(int i = 0; i < 5; i++){
            PokerUtility.commonCards.add(Deck.dealCard());
        }
    }

    public static ArrayList<Card> determineWinners(ArrayList<ArrayList<Card>> combos) {
        return null;
    }

    public static void printAllCombos(ArrayList<ArrayList<Card>> combos) {
        for(int i = 0; i < combos.size();i++){
            System.out.println(combos.get(i));
        }
    }

    public static ArrayList<Card> sortOneArray(ArrayList<Card> arr){
        int n = arr.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr.get(j).value > arr.get(j+1).value) {
                    Card temp = arr.get(j);
                    arr.set(j, arr.get(j+1));
                    arr.set(j+1, temp);
                }
            }
        }
        return arr;
    }

    public static ArrayList<ArrayList<Card>> sortAllArrays(ArrayList<ArrayList<Card>> arrCombos){
        for(int i = 0; i< arrCombos.size(); i ++){
            sortOneArray(arrCombos.get(i));
        }
        return arrCombos;
    }

    public static boolean isStraight (ArrayList<Card> hand){
        if(hand.get(4).value == 14 && hand.get(2).value == 2 && hand.get(3).value == 3 &&
           hand.get(4).value == 4){

        }
        if(hand.get(0).value == hand.get(1).value-1 && hand.get(1).value-1 == hand.get(2).value-2 &&
        hand.get(2).value-2 == hand.get(3).value-3 && hand.get(3).value-3  == hand.get(4).value-4){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isFlush (ArrayList<Card> hand){
        if(hand.get(0).suit.equals(hand.get(1).suit) && hand.get(1).suit.equals(hand.get(2).suit)&&
        hand.get(2).suit.equals(hand.get(3).suit) && hand.get(3).suit.equals(hand.get(4).suit)){
            return true;
        }
        return false;
    }
    public static boolean isPair (ArrayList<Card> hand) {
        if (hand.get(0).value == hand.get(1).value || hand.get(1).value == hand.get(2).value ||
                hand.get(2).value == hand.get(3).value || hand.get(3).value == hand.get(4).value) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isTriple (ArrayList<Card> hand){
            if(hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value ||
            hand.get(1).value == hand.get(2).value && hand.get(2).value == hand.get(3).value ||
            hand.get(2).value == hand.get(3).value && hand.get(3).value == hand.get(4).value ){
                return true;
            }
            else{
                return false;
            }
        }
    public static boolean isQuadruple (ArrayList<Card> hand){
        if(hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value && hand.get(2).value == hand.get(3).value ||
        hand.get(1).value == hand.get(2).value && hand.get(2).value == hand.get(3).value && hand.get(3).value == hand.get(4).value){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isFullHouse (ArrayList<Card> hand){
        if(hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value &&
                hand.get(3).value == hand.get(4).value ||
                hand.get(2).value == hand.get(3).value && hand.get(3).value == hand.get(4).value &&
                hand.get(0).value == hand.get(1).value){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isStraightFlush (ArrayList<Card> hand){
        if(isFlush(hand) && isStraight(hand)){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean isTwoPair (ArrayList<Card> hand) {
        if (hand.get(0).value == hand.get(1).value && hand.get(2).value == hand.get(3).value ||
                hand.get(0).value == hand.get(1).value && hand.get(3).value == hand.get(4).value ||
                hand.get(1).value == hand.get(2).value && hand.get(3).value == hand.get(4).value) {
            return true;
        } else {
            return false;
        }
    }

    public static double scoreOfHand (ArrayList<Card> hand){
        if(isStraightFlush(hand)){
            return ScoreEvaluators.straightFlushScoreCalculate(hand);
        }
        else if(isQuadruple(hand)){
            return ScoreEvaluators.fourOfAKindScoreCalculate(hand);
        }
        else if(isFullHouse(hand)){
            return ScoreEvaluators.fullHouseScoreCalculate(hand);
        }
        else if(isFlush(hand)){
            return ScoreEvaluators.flushScoreCalculate(hand);
        }
        else if(isStraight(hand)){
            return ScoreEvaluators.straightScoreCalculate(hand);
        }
        else if(isTriple(hand)){
            return ScoreEvaluators.threeOfAKindScoreCalculate(hand);
        }
        else if(isTwoPair(hand)){
            return ScoreEvaluators.doublePairScoreCalculate(hand);
        }
        else if(isPair(hand)){
            return ScoreEvaluators.pairScoreCalculate(hand);
        }
        else{
            return ScoreEvaluators.highCardScoreCalculate(hand);
        }
    }

    public static ArrayList<Card> pickBestCombo (ArrayList<ArrayList<Card>> allCombos){
        double tempScore;
        int bestIndex = 0;
        double bestScore = 0;
        for(int i = 0; i < allCombos.size(); i++){
            tempScore = scoreOfHand(allCombos.get(i));
            if(tempScore>bestScore){
                bestScore = tempScore;
                bestIndex = i;
            }
        }
        return allCombos.get(bestIndex);
    }

    public static double getScoreForPlayer (Player p){
         ArrayList<ArrayList<Card>> tempCombos = makeAllHandCombinations(p);
         ArrayList<Card> tempHand = pickBestCombo(tempCombos);
         return scoreOfHand(tempHand);
    }

}


