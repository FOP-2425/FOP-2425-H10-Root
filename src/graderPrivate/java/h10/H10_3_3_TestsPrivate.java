package h10;

import h10.util.MockCardGame;
import h10.util.MockCardGamePlayer;
import h10.util.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Collection;
import java.util.List;

/**
 * Tests for H10.3.3.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.3.3 | Verlierer des Spiels bestimmen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_3_TestsPrivate extends H10_3_3_TestsPublic {

    @DisplayName("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt.")
    @Test
    void testDrawTwoLastTurnNoDrawTwo() throws Throwable {
        MockCardGamePlayer player = players.getFirst();
        player.takeCard(PlayingCard.DRAW_TWO);
        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });
        MockCardGamePlayer nextPlayer = players.get(1);

        MockCardGame game = new MockCardGame(
            players.stream().map(p -> (CardGamePlayer) p).toList(),
            List.of(PlayingCard.PASS, PlayingCard.SKIP, PlayingCard.DRAW_TWO, PlayingCard.PASS)
        );


        Context context = contextBuilder()
            .add("Players", game.getPlayers())
            .add("Deck", game.getDeck())
            .add("Current player", player)
            .add("Next player", nextPlayer)
            .add("Playing card", PlayingCard.DRAW_TWO)
            .build();

        int numberOfCards = nextPlayer.getHandSize();
        // Current player turn
        getMethod().invoke(game);

        // Next player turn
        getMethod().invoke(game);
        // Since he plays one card, he should draw two cards = 1 new card
        Assertions2.assertEquals(numberOfCards + 1, nextPlayer.getHandSize(), context, result -> "The next player should draw two cards.");
    }

    @DisplayName("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt.")
    @Test
    void testDrawTwoLastTurnDrawTwo() throws Throwable {
        MockCardGamePlayer player = players.getFirst();
        player.takeCard(PlayingCard.DRAW_TWO);
        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });
        MockCardGamePlayer nextPlayer = players.get(1);
        nextPlayer.takeCard(PlayingCard.DRAW_TWO);

        MockCardGamePlayer thirdPlayer = players.get(2);

        MockCardGame game = new MockCardGame(
            players.stream().map(p -> (CardGamePlayer) p).toList(),
            List.of(PlayingCard.PASS, PlayingCard.SKIP, PlayingCard.DRAW_TWO, PlayingCard.PASS)
        );

        Context context = contextBuilder()
            .add("Players", game.getPlayers())
            .add("Deck", game.getDeck())
            .add("Current player", player)
            .add("Next player", nextPlayer)
            .add("Third player", thirdPlayer)
            .add("Playing card", PlayingCard.DRAW_TWO)
            .add("Playing card next", PlayingCard.DRAW_TWO)
            .build();

        int numberOfCardsNext = nextPlayer.getHandSize();
        int numberOfCardsThird = thirdPlayer.getHandSize();
        // Current player turn
        getMethod().invoke(game);

        // Next player turn
        getMethod().invoke(game);

        // Third player turn
        getMethod().invoke(game);

        Assertions2.assertEquals(numberOfCardsNext-1, nextPlayer.getHandSize(), context, result -> "The next player should not draw any cards.");
        // Since he plays one card, he should draw four cards = 3 new card
        Assertions2.assertEquals(numberOfCardsThird + 3, thirdPlayer.getHandSize(), context, result -> "The next player should draw two cards.");
    }

    @DisplayName("Wurden in den vorherigen Zügen mehrere DRAW_TWO-Karten gespielt, so erhöht sich die Anzahl der zu ziehenden Karten entsprechend.")
    @Test
    void testDrawTwoMultiple() throws Throwable {
        MockCardGamePlayer player = players.getFirst();
        player.takeCard(PlayingCard.DRAW_TWO);
        player.takeCard(PlayingCard.SKIP);

        MockCardGamePlayer nextPlayer = players.get(1);
        nextPlayer.takeCard(PlayingCard.SKIP);
        nextPlayer.takeCard(PlayingCard.DRAW_TWO);

        MockCardGamePlayer thirdPlayer = players.get(2);
        thirdPlayer.takeCard(PlayingCard.DRAW_TWO);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream().map(MockCardGamePlayer::getHand).flatMap(Collection::stream).toList();
        MockCardGame game = new MockCardGame(players.stream().map(p -> (CardGamePlayer) p).toList(), deck);

        int size = nextPlayer.getHandSize();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", player)
            .add("Next player", nextPlayer)
            .add("Playing cards", List.of(PlayingCard.DRAW_TWO,PlayingCard.DRAW_TWO,PlayingCard.DRAW_TWO))
            .build();

        // Current player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(2, game.getTakeCards(), context, result -> "The number of cards to draw should be 2.");

        // Next player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(4, game.getTakeCards(), context, result -> "The number of cards to draw should be 4.");

        // Third player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(6, game.getTakeCards(), context, result -> "The number of cards to draw should be 6.");
    }

    @DisplayName("Spieler, die keine Karten mehr auf der Hand haben, werden aus dem Spiel entfernt.")
    @Test
    void testNoCards() throws Throwable {
        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });
        List<PlayingCard> deck = players.stream().map(MockCardGamePlayer::getHand).flatMap(Collection::stream).toList();
        MockCardGame game = new MockCardGame(players.stream().map(p -> (CardGamePlayer) p).toList(), deck);

        MockCardGamePlayer player = players.getFirst();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", player)
            .add("Playing card", PlayingCard.PASS)
            .build();

        int numberOfPlayers = players.size();
        // Current player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(0, player.getHandSize(), context, result -> "The player should not have any card.");
        Assertions2.assertEquals(numberOfPlayers - 1, game.getPlayers().size(), context, result -> "The player should be removed from the game.");
    }

}
