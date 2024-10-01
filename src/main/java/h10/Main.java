package h10;

public class Main {
    public static void main(String[] args) {
        CardGame game = CardGame.generateRandomCardGame();
        System.out.println(game);
        CardGamePlayer loser = game.determineLoser();
        System.out.println("The loser is: " + loser.getName());
    }
}
