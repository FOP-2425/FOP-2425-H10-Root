package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a simple card game with 4 players and a deck of cards
 */
public class CardGame {
    @DoNotTouch
    private DoublyLinkedList<CardGamePlayer> players;
    @DoNotTouch
    private BidirectionalIterator<CardGamePlayer> iter;
    @DoNotTouch
    private DoublyLinkedList<PlayingCard> cardDeck;

    // Game state variables
    @DoNotTouch
    private boolean reverseDirection = false; // If true, the direction of play is reversed in the next turn
    @DoNotTouch
    private boolean skipNextPlayer = false; // If true, the next player is skipped
    @DoNotTouch
    private int takeCards = 0; // Number of cards the next player has to draw
    @DoNotTouch
    private CardGamePlayer currentPlayer = null; // The player whose turn it is
    @DoNotTouch
    private PlayingCard currentCard = null; // The card that is played in the current turn

    @DoNotTouch
    private CardGame() {}

    /**
     * Creates a new card game with the given players and card deck
     */
    @DoNotTouch
    public CardGame(DoublyLinkedList<CardGamePlayer> players, DoublyLinkedList<PlayingCard> cardDeck) {
        this.players = players;
        this.cardDeck = cardDeck;
        this.iter = this.players.cyclicIterator();
    }

    /**
     * Creates a new card game with 4 players and a deck of 100 cards
     * The deck is shuffled and each player gets 5 random cards
     */
    @DoNotTouch
    public static CardGame generateRandomCardGame() {
        CardGame cardGame = new CardGame();

        cardGame.players = new DoublyLinkedList<>();
        cardGame.cardDeck = new DoublyLinkedList<>();

        // Create card deck with 100 random cards
        PlayingCard[] cards = PlayingCard.values();
        for (int i = 0; i < 100; i++) {
            cardGame.cardDeck.add(cards[(int)(Math.random() * 4)]);
        }

        // 4 players with 5 cards each
        for ( int i = 1; i < 5; i++) {
            CardGamePlayer player = new CardGamePlayer("Player " + i);
            cardGame.players.add(player);
            for (int j = 0; j < 5; j++) {
                player.takeCard(cardGame.cardDeck.removeAtIndex(0));
            }
        }

        cardGame.iter = cardGame.players.cyclicIterator();

        return cardGame;
    }

    /**
     * Determines the loser of the game
     * @return the last player who still has cards in their hand
     */
    @DoNotTouch
    public CardGamePlayer determineLoser() {
        while(players.size() > 1) {
            doTurn();
        }

        return players.get(0);
    }

    /**
     * Executes a turn in the game.
     */
    @StudentImplementationRequired
    private void doTurn() {
        currentPlayer = reverseDirection ? iter.previous() : iter.next();

        if(skipNextPlayer) {
            skipNextPlayer = false;
            return;
        }

        // the player plays the next card in their hand
        boolean prioritizeDrawTwo = PlayingCard.DRAW_TWO.equals(currentCard);
        currentCard = currentPlayer.playNextCard(prioritizeDrawTwo);

        if(PlayingCard.DRAW_TWO.equals(currentCard)) {
            takeCards += 2;
        } else if(takeCards > 0) {
            for (int i = 0; i < takeCards; i++) {
                currentPlayer.takeCard(cardDeck.removeAtIndex(0));
            }
            takeCards = 0;
        }

        if (PlayingCard.SKIP.equals(currentCard)) { // Skip next player
            skipNextPlayer = true;
        }
        if (PlayingCard.REVERSE.equals(currentCard)) { // Change of direction
            reverseDirection = !reverseDirection;
        }

        if (currentPlayer.getHandSize() == 0) { // This player is out of the game (is not the loser)
            iter.remove();
        }
    }

    @Override
    @DoNotTouch
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            sb.append(players.get(i).toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
