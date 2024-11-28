package h10;

import h10.assertions.TestConstants;
import h10.rubric.H10_Tests;
import h10.util.CardGames;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the public tests for H10.3.3.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.3.3 | Verlierer des Spiels bestimmen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_3_TestsPublic extends H10_Tests {

    @Override
    public Class<?> getClassType() {
        return CardGame.class;
    }

    @Override
    public String getMethodName() {
        return "doTurn";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of();
    }

    /**
     * Converts a MyList to a List.
     *
     * @param myList the MyList to convert
     * @param <T>    the type of the elements in the list.
     * @return the converted List.
     */
    protected <T> List<T> fromMyList(MyList<T> myList) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            list.add(myList.get(i));
        }
        return list;
    }

    @DisplayName("Bei einem SKIP wird der nächste Spieler übersprungen.")
    @Test
    void testSkip() throws Throwable {
        CardGamePlayer playerOne = new CardGamePlayer("Lisa");
        CardGamePlayer playerTwo = new CardGamePlayer("Jennie");
        CardGamePlayer playerThree = new CardGamePlayer("Rosie");
        CardGamePlayer playerFour = new CardGamePlayer("Jisoo");

        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);

        playerOne.takeCard(PlayingCard.SKIP);
        playerOne.takeCard(PlayingCard.SKIP);

        playerTwo.takeCard(PlayingCard.SKIP);
        playerTwo.takeCard(PlayingCard.SKIP);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList).flatMap(List::stream).
            toList();

        CardGame game = CardGames.create(players, deck);

        int nextHandSize = playerTwo.getHandSize();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Next player", playerTwo)
            .add("Playing card", PlayingCard.SKIP)
            .build();

        // Current player turn
        getMethod().invoke(game);
        // Check if skip is correctly set
        Assertions2.assertTrue(game.skipNextPlayer, context, result -> "The game should skip the next player.");

        // Next player turn
        getMethod().invoke(game);

        // Check if the next player is skipped
        Assertions2.assertSame(playerTwo, game.currentPlayer, context,
            result -> "The current player should be the next player.");

        Assertions2.assertFalse(game.skipNextPlayer, context,
            result -> "Skip player should be reset after skipping the next player.");
        Assertions2.assertEquals(nextHandSize, nextHandSize, context,
            result -> "The next player should not play any cards.");
    }

    @DisplayName("Bei einer REVERSE-Karte wird die Richtung des Iterators umgekehrt.")
    @Test
    void testReverse() throws Throwable {
        CardGamePlayer playerOne = new CardGamePlayer("Lisa");
        CardGamePlayer playerTwo = new CardGamePlayer("Jennie");
        CardGamePlayer playerThree = new CardGamePlayer("Rosie");
        CardGamePlayer playerFour = new CardGamePlayer("Jisoo");
        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);

        playerOne.takeCard(PlayingCard.REVERSE);
        playerOne.takeCard(PlayingCard.SKIP);

        playerTwo.takeCard(PlayingCard.SKIP);
        playerTwo.takeCard(PlayingCard.SKIP);

        playerFour.takeCard(PlayingCard.SKIP);
        playerFour.takeCard(PlayingCard.SKIP);

        playerTwo.takeCard(PlayingCard.SKIP);
        playerTwo.takeCard(PlayingCard.SKIP);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList).flatMap(List::stream).
            toList();
        CardGame game = CardGames.create(players, deck);

        int successorPlayerHandSize = playerTwo.getHandSize();
        int predecessorPlayerHandSize = playerFour.getHandSize();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Next player", playerFour)
            .add("Playing card", PlayingCard.REVERSE)
            .build();

        // Current player turn
        getMethod().invoke(game);

        // Check if skip is correctly set
        Assertions2.assertTrue(game.reverseDirection, context, result -> "The game should reverse the direction.");

        // Last player turn
        getMethod().invoke(game);
        Assertions2.assertSame(playerFour, game.currentPlayer, context, result -> "The current player should be the last player.");
        Assertions2.assertEquals(successorPlayerHandSize, playerTwo.getHandSize(), context,
            result -> "The last player should not play any card.");
        Assertions2.assertEquals(predecessorPlayerHandSize - 1, playerFour.getHandSize(), context,
            result -> "The last player should play one card.");
    }

    @DisplayName("Spieler, die keine Karten mehr auf der Hand haben, werden aus dem Spiel entfernt.")
    @Test
    void testNoCards() throws Throwable {
        CardGamePlayer playerOne = new CardGamePlayer("Lisa");
        CardGamePlayer playerTwo = new CardGamePlayer("Jennie");
        CardGamePlayer playerThree = new CardGamePlayer("Rosie");
        CardGamePlayer playerFour = new CardGamePlayer("Jisoo");
        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);
        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });
        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList).flatMap(List::stream).
            toList();
        CardGame game = CardGames.create(players, deck);


        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Playing card", PlayingCard.PASS)
            .build();

        int numberOfPlayers = players.size();
        // Current player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(0, playerOne.getHandSize(), context, result -> "The player should not have any card.");
        Assertions2.assertEquals(numberOfPlayers - 1, game.players.size(),
            context, result -> "The player should be removed from the game.");
    }
}
