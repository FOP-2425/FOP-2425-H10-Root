package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a simple card game with 4 players and a deck of cards
 */
public class CardGame {
    private final DoublyLinkedList<CardGamePlayer> players;
    private final DoublyLinkedList<PlayingCard> cardDeck;

    /**
     * Creates a new card game with 4 players and a deck of 100 cards
     * The deck is shuffled and each player gets 5 random cards
     */
    public CardGame() {
        players = new DoublyLinkedList<>();
        cardDeck = new DoublyLinkedList<>();

        // Create card deck with 100 random cards
        PlayingCard[] cards = PlayingCard.values();
        for (int i = 0; i < 100; i++) {
            cardDeck.add(cards[(int)(Math.random() * 4)]);
        }

        // 4 players with 5 cards each
        for ( int i = 1; i < 5; i++) {
            CardGamePlayer player = new CardGamePlayer("Player " + i);
            players.add(player);
            for (int j = 0; j < 5; j++) {
                player.takeCard(cardDeck.removeAtIndex(0));
            }
        }
    }

    /**
     * Determines the loser of the game
     * @return the last player who still has cards in their hand
     */
    @StudentImplementationRequired
    public CardGamePlayer determineLoser() {
        DoublyLinkedList<CardGamePlayer>.CyclicIterator iter = players.cyclicIterator();

        boolean reverseDirection = false;
        boolean skipNextPlayer = false;
        int takeCards = 0;
        CardGamePlayer currentPlayer;

        while(players.size() > 1) {
            currentPlayer = reverseDirection ? iter.previous() : iter.next();

            if(skipNextPlayer) {
                skipNextPlayer = false;
                continue;
            }

            // the players just play the next card in their hand
            PlayingCard currentCard = currentPlayer.playNextCard();

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

            if (currentPlayer.getHandSize() == 0) { // This player won and is out of the game
                iter.remove();
            }
        }

        return players.get(0);
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
