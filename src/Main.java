
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        OddsResult r = Round.calculateOdds(13, "Spades", 13, "Clubs", 4, 50_000);
        System.out.printf("Win: %.2f%%  Tie: %.2f%%  Lose: %.2f%%%n", r.win, r.tie, r.lose);
        //Round.simulateMultipleRounds(12, "Spades", 14, "Clubs", 4, 50000);
    }

}
