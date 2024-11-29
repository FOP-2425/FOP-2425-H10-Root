package h10;

import h10.assertions.Links;
import h10.util.MockList;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

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

    /**
     * Creates a new card game player.
     *
     * @param name the name of the player
     * @return the new card game player
     */
    public static CardGamePlayer createPlayer(String name) {
        CardGamePlayer player = new CardGamePlayer(name);
        FieldLink hand = Links.getField(CardGamePlayer.class, "hand");
        hand.set(player, new MockList<>());
        return player;
    }
}
