package h10;

public class Main {
    public static void main(String[] args) {
        CardGame game = new CardGame();
        System.out.println(game);
        CardGamePlayer loser = game.determineLoser();
        System.out.println("The loser is: " + loser.getName());
    }
}
