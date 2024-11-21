package h10;

import h10.util.MockCardGame;
import h10.util.MockCardGamePlayer;
import h10.util.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class H10_3_3_TestsPublic extends H10_Test {

    List<MockCardGamePlayer> players;

    @BeforeEach
    void setup() {
        players = List.of(
            new MockCardGamePlayer("Alice"),
            new MockCardGamePlayer("Bob"),
            new MockCardGamePlayer("Charlie")
        );
    }

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

    @DisplayName("Der Spieler, der als letztes Karten in der Hand hat, wird korrekt bestimmt und zurückgegeben.")
    @Test
    void testLastCards() throws Throwable {
        Assertions2.fail(contextBuilder().build(), result -> "Not implemented");
    }

    @DisplayName("Bei einem SKIP wird der nächste Spieler übersprungen.")
    @Test
    void testSkip() throws Throwable {
        MockCardGamePlayer player = players.getFirst();
        player.takeCard(PlayingCard.SKIP);
        player.takeCard(PlayingCard.SKIP);

        MockCardGamePlayer nextPlayer = players.get(1);
        nextPlayer.takeCard(PlayingCard.SKIP);
        nextPlayer.takeCard(PlayingCard.SKIP);

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
            .add("Playing card", PlayingCard.SKIP)
            .build();

        // Current player turn
        getMethod().invoke(game);
        // Check if skip is correctly set
        Assertions2.assertTrue(game.getSkipNextPlayer(), context, result -> "The game should skip the next player.");

        // Next player turn
        getMethod().invoke(game);
        // Check if next player is skipped
        Assertions2.assertSame(nextPlayer, game.getCurrentPlayer(), context, result -> "The current player should be the next player.");

        Assertions2.assertFalse(game.getSkipNextPlayer(), context, result -> "Skip player should be reset after skipping the next player.");
        Assertions2.assertEquals(size, nextPlayer.getHandSize(), context, result -> "The next player should not play any cards.");
    }

    @DisplayName("Bei einer REVERSE-Karte wird die Richtung des Iterators umgekehrt.")
    @Test
    void testReverse() throws Throwable {
        MockCardGamePlayer player = players.getFirst();
        player.takeCard(PlayingCard.REVERSE);
        player.takeCard(PlayingCard.SKIP);

        MockCardGamePlayer nextPlayer = players.get(1);
        nextPlayer.takeCard(PlayingCard.SKIP);
        nextPlayer.takeCard(PlayingCard.SKIP);

        MockCardGamePlayer lastPlayer = players.getLast();
        lastPlayer.takeCard(PlayingCard.SKIP);
        nextPlayer.takeCard(PlayingCard.SKIP);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream().map(MockCardGamePlayer::getHand).flatMap(Collection::stream).toList();
        MockCardGame game = new MockCardGame(players.stream().map(p -> (CardGamePlayer) p).toList(), deck);

        int nextPlayersHandSize = nextPlayer.getHandSize();
        int lastPlayersHandSize = lastPlayer.getHandSize();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", player)
            .add("Next player", lastPlayer)
            .add("Playing card", PlayingCard.REVERSE)
            .build();

        // Current player turn
        getMethod().invoke(game);
        // Check if skip is correctly set
        Assertions2.assertTrue(game.getReverseDirection(), context, result -> "The game should reverse the direction.");

        // Last player turn
        getMethod().invoke(game);
        Assertions2.assertSame(lastPlayer, game.getCurrentPlayer(), context, result -> "The current player should be the last player.");
        Assertions2.assertEquals(nextPlayersHandSize, nextPlayer.getHandSize(), context, result -> "The last player should not play any card.");
        Assertions2.assertEquals(lastPlayersHandSize - 1, lastPlayer.getHandSize(), context, result -> "The last player should play one card.");
    }

}
