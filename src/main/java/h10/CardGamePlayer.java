package h10;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

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

    @StudentImplementationRequired
    public PlayingCard playNextCard() {
        return hand.removeAtIndex(0);
    }

    @StudentImplementationRequired
    public void takeCard(PlayingCard card) {
        hand.add(card);
    }

    public int getHandSize() {
        return hand.size();
    }
}
