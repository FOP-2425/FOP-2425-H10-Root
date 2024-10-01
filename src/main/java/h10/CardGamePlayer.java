package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a player in the card game
 */
@DoNotTouch
public class CardGamePlayer {
    @DoNotTouch
    private final String name;
    @DoNotTouch
    private final DoublyLinkedList<PlayingCard> hand;

    @DoNotTouch
    public CardGamePlayer(String name) {
        this.name = name;
        this.hand = new DoublyLinkedList<>();
    }

    @DoNotTouch
    public String getName() {
        return name;
    }

    /**
     * Plays the next card from the player's hand
     * @param prioritizeDrawTwo if true, the player will prioritize playing a DRAW_TWO card
     * @return the card that was played
     */
    @DoNotTouch
    public PlayingCard playNextCard(boolean prioritizeDrawTwo) {
        if(prioritizeDrawTwo) {
            int index = hand.findFirst(PlayingCard.DRAW_TWO);

            if(index != -1) { // Player has a DRAW_TWO card on his hand, prioritize this card
                return hand.removeAtIndex(index);
            }
        }

        return hand.removeAtIndex(0);
    }

    /**
     * Adds a card to the player's hand
     * @param card the card to add
     */
    @DoNotTouch
    public void takeCard(PlayingCard card) {
        hand.add(card);
    }

    /**
     * Returns the number of cards in the player's hand
     */
    @DoNotTouch
    public int getHandSize() {
        return hand.size();
    }

    @Override
    @DoNotTouch
    public String toString() {
        return name + ": " + hand;
    }
}
