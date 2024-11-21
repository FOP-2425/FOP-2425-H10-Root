package h10.util;

import h10.CardGame;
import h10.CardGamePlayer;
import h10.List;
import h10.PlayingCard;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class MockCardGame extends CardGame {

    private final FieldLink players;
    private final FieldLink deck;
    private final FieldLink reverseDirection;
    private final FieldLink skipNextPlayer;
    private final FieldLink currentPlayer;
    private final FieldLink takeCards;

    public MockCardGame(java.util.List<CardGamePlayer> players, java.util.List<PlayingCard> cardDeck) {
        super(MockList.of(players), MockList.of(cardDeck));
        Class<?> clazz = CardGame.class;
        this.players = Links.getField(clazz, "players");
        this.deck = Links.getField(clazz, "cardDeck");
        this.reverseDirection = Links.getField(clazz, "reverseDirection");
        this.skipNextPlayer = Links.getField(clazz, "skipNextPlayer");
        this.currentPlayer = Links.getField(clazz, "currentPlayer");
        this.takeCards = Links.getField(clazz, "takeCards");
    }

    public boolean getReverseDirection() {
        return reverseDirection.get(this);
    }

    public boolean getSkipNextPlayer() {
        return skipNextPlayer.get(this);
    }

    public CardGamePlayer getCurrentPlayer() {
        return currentPlayer.get(this);
    }

    public int getTakeCards() {
        return takeCards.get(this);
    }

    public void setTakeCards(int takeCards) {
        this.takeCards.set(this, takeCards);
    }

    public java.util.List<CardGamePlayer> getPlayers() {
        java.util.List<CardGamePlayer> mapped = new ArrayList<>();
        List<CardGamePlayer> result = players.get(this);
        for (int i = 0; i < result.size(); i++) {
            mapped.add(result.get(i));
        }
        return mapped;
    }

    public java.util.List<PlayingCard> getDeck() {
        java.util.List<PlayingCard> mapped = new ArrayList<>();
        List<PlayingCard> result = deck.get(this);
        for (int i = 0; i < result.size(); i++) {
            mapped.add(result.get(i));
        }
        return mapped;
    }
}
