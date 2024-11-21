package h10.util;

import h10.CardGamePlayer;
import h10.MyList;
import h10.PlayingCard;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

import java.util.ArrayList;

public class MockCardGamePlayer extends CardGamePlayer {


    private final FieldLink hand;

    public MockCardGamePlayer(String name) {
        super(name);
        this.hand = Links.getField(CardGamePlayer.class, "hand");
        setHand(new MockList<>());
    }

    public java.util.List<PlayingCard> getHand() {
        java.util.List<PlayingCard> mapped = new ArrayList<>();
        MyList<PlayingCard> cards = hand.get(this);
        for (int i = 0; i < cards.size(); i++) {
            mapped.add(cards.get(i));
        }
        return mapped;
    }

    public void setHand(MyList<PlayingCard> hand) {
        this.hand.set(this, hand);
    }
}
