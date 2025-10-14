import java.util.ArrayList;

public class Player {


    String name;

    Card holeCard1;
    Card holeCard2;



    public Player() {
    }

    public Player(Card pocketCard1, Card pocketCard2) {
        this.holeCard1 = pocketCard1;
        this.holeCard2 = pocketCard2;
    }


}
