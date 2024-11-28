package h10.util;

import h10.CardGame;
import h10.CardGamePlayer;
import h10.MyList;
import h10.PlayingCard;

import java.util.List;

/**
 * Utility class for card games.
 *
 * @author Nhan Huynh
 */
public final class CardGames {

    /**
     * Prevent instantiation of this utility class.
     */
    private CardGames() {
    }

    /**
     * Creates a new card game from the given players and card deck.
     *
     * @param players  the players to create the game with
     * @param cardDeck the card deck to create the game with
     * @return the new card game
     */
    public static CardGame create(List<CardGamePlayer> players, List<PlayingCard> cardDeck) {
        MyList<CardGamePlayer> playerList = new MockList<>();
        players.forEach(playerList::add);
        MyList<PlayingCard> cardList = new MockList<>();
        cardDeck.forEach(cardList::add);
        return new CardGame(playerList, cardList);
    }
}
