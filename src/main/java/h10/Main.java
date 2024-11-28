package h10;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        // Manual testing
        CardGame game = CardGame.generateRandomCardGame();
        System.out.println(game);
        CardGamePlayer loser = game.determineLoser();
        System.out.println("The loser is: " + loser.getName());
    }
}
