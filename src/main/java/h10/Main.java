package h10;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        CardGame game = CardGame.generateRandomGame();
        CardGamePlayer loser = game.determineLoser();
        System.out.println("The loser is: " + loser.getName());
    }
}
