package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a player in the card game
 */
@DoNotTouch
public class CardGamePlayer {
    private final String name;
    private final DoublyLinkedList<PlayingCard> hand;

    public CardGamePlayer(String name) {
        this.name = name;
        this.hand = new DoublyLinkedList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Plays the next card in the player's hand
     */
    public PlayingCard playNextCard() {
        return hand.removeAtIndex(0);
    }

    /**
     * Adds a card to the player's hand
     * @param card the card to add
     */
    public void takeCard(PlayingCard card) {
        hand.add(card);
    }

    /**
     * Returns the number of cards in the player's hand
     */
    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        return name + ": " + hand;
    }
}
