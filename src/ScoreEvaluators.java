import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreEvaluators {

    public static double straightFlushScoreCalculate(ArrayList<Card> hand){
        double straightFlushScore;
        straightFlushScore = hand.get(4).value*10 + Math.pow(10,28);
        return straightFlushScore;
    }

    public static double fourOfAKindScoreCalculate(ArrayList<Card> hand){
        double fourOfAKindScore;
        if(hand.get(0).value == hand.get(1).value){
            fourOfAKindScore = hand.get(0).value*Math.pow(10,20) + hand.get(4).value*Math.pow(10,17) + Math.pow(10,25);
        }
        else{
            fourOfAKindScore = hand.get(4).value*Math.pow(10,20) + hand.get(0).value*Math.pow(10,17) + Math.pow(10,25);
        }
        return fourOfAKindScore;
    }

    public static double fullHouseScoreCalculate(ArrayList<Card> hand){
        double fullHouseScore;
        if(hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value && hand.get(3).value == hand.get(4).value){
            fullHouseScore = hand.get(0).value*Math.pow(10,12) + hand.get(3).value*Math.pow(10,9) + Math.pow(10,22);
            }
        else if(hand.get(2).value == hand.get(3).value && hand.get(3).value == hand.get(4).value && hand.get(0).value == hand.get(1).value){
            fullHouseScore = hand.get(3).value*Math.pow(10,12) + hand.get(0).value*Math.pow(10,9) + Math.pow(10,22);
        }
        else {
            return 0;
        }
        return fullHouseScore;
    }

    public static double flushScoreCalculate(ArrayList<Card> hand){
        double flushScore;
        flushScore = hand.get(4).value * Math.pow(10,9) + hand.get(3).value * Math.pow(10,7) +
                hand.get(2).value * Math.pow(10,5) +hand.get(1).value * Math.pow(10,3) +
                hand.get(0).value * Math.pow(10,1) + Math.pow(10,19);
        return flushScore;
    }

    public static double straightScoreCalculate(ArrayList<Card> hand){
        double straightScore;
        straightScore = hand.get(4).value * Math.pow(10,9) + hand.get(3).value * Math.pow(10,7) +
                        hand.get(2).value * Math.pow(10,5) +hand.get(1).value * Math.pow(10,3) +
                        hand.get(0).value * Math.pow(10,1) + Math.pow(10,16);
        return straightScore;
    }

    public static double threeOfAKindScoreCalculate(ArrayList<Card> hand){
        double threeOfAKindScore;
        if(hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value){
            threeOfAKindScore = hand.get(0).value*100000 + hand.get(4).value*1000+
                                hand.get(3).value*10 + Math.pow(10,13);
        }
        else if (hand.get(1).value== hand.get(2).value && hand.get(2).value == hand.get(3).value){
            threeOfAKindScore = hand.get(1).value*100000 + hand.get(4).value*1000+
                                hand.get(0).value*10 + Math.pow(10,13);
        }
        else {
            threeOfAKindScore = hand.get(2).value*100000 + hand.get(1).value*1000+
                                hand.get(0).value*10 + Math.pow(10,13);
        }
        return threeOfAKindScore;
    }

    public static double doublePairScoreCalculate (ArrayList<Card> hand){
        double doublePairScore;
        if(hand.get(0).value == hand.get(1).value && hand.get(2).value == hand.get(3).value){
            doublePairScore = hand.get(3).value*100000 + hand.get(1).value*1000 +
                              hand.get(4).value + Math.pow(10,10);
        }
        else if (hand.get(1).value == hand.get(2).value && hand.get(3).value == hand.get(4).value) {
            doublePairScore = hand.get(4).value*100000 + hand.get(2).value*1000 +
                              hand.get(0).value + Math.pow(10,10);
        }
        else{
            doublePairScore = hand.get(4).value*100000 + hand.get(1).value*1000 +
                              hand.get(2).value + Math.pow(10,10);
        }
        return doublePairScore;
    }

    public static double pairScoreCalculate (ArrayList<Card > hand){
        double pairScore;
        if(hand.get(0).value == hand.get(1).value){
            pairScore = hand.get(1).value*Math.pow(10,6) + hand.get(4).value*Math.pow(10,4)+
                        hand.get(3).value*Math.pow(10,2)+hand.get(2).value*Math.pow(10,0) + Math.pow(10,7);
        }
        else if (hand.get(1).value == hand.get(2).value){
            pairScore = hand.get(2).value*Math.pow(10,6) + hand.get(4).value*Math.pow(10,4)+
                        hand.get(3).value*Math.pow(10,2)+hand.get(0).value*Math.pow(10,0) + Math.pow(10,7);
        }
        else if (hand.get(2).value == hand.get(3).value) {
            pairScore = hand.get(3).value*Math.pow(10,6) + hand.get(4).value*Math.pow(10,4)+
                        hand.get(1).value*Math.pow(10,2)+hand.get(0).value*Math.pow(10,0)+ Math.pow(10,7);
        }
        else{
            pairScore = hand.get(4).value*Math.pow(10,6) + hand.get(2).value*Math.pow(10,4)+
                        hand.get(1).value*Math.pow(10,2)+hand.get(0).value*Math.pow(10,0) + Math.pow(10,7);

        }
        return pairScore;
    }
    public static double highCardScoreCalculate(ArrayList<Card> hand){
        double highCardScore;
        highCardScore = hand.get(4).value * Math.pow(10,5) + hand.get(3).value * Math.pow(10,3) +
                hand.get(2).value * Math.pow(10,1) +hand.get(1).value * Math.pow(10,-1) +
                hand.get(0).value * Math.pow(10,-3) + Math.pow(10,4);
        return highCardScore;
    }


}


