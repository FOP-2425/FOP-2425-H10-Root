package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.Links;
import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the public tests for H10.3.3.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.3.3 | Verlierer des Spiels bestimmen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_3_TestsPrivate extends H10_3_3_TestsPublic {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "deck", JsonConverters::toDeck,
        "players", node -> JsonConverters.toList(node, JsonConverters::toPlayer),
        "loser", JsonNode::intValue
    );

    @Test
    void test() {
        CtElement element = ((BasicMethodLink) getMethod()).getCtElement();
        element.filterChildren(e -> e instanceof CtInvocation<?>).forEach(System.out::println);

    }

    @DisplayName("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt.")
    @Test
    void testDrawTwoLastTurnNoDrawTwo() throws Throwable {
        CardGamePlayer playerOne = CardGames.createPlayer("Lisa");
        CardGamePlayer playerTwo = CardGames.createPlayer("Jennie");
        CardGamePlayer playerThree = CardGames.createPlayer("Rosie");
        CardGamePlayer playerFour = CardGames.createPlayer("Jisoo");
        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);

        playerOne.takeCard(PlayingCard.DRAW_TWO);
        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList)
            .flatMap(List::stream)
            .toList();

        CardGame game = H10_1_1_TestsPublic.CardGames.create(players, deck);


        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Next player", playerTwo)
            .add("Playing card", PlayingCard.DRAW_TWO)
            .build();

        int numberOfCards = playerTwo.getHandSize();

        // Current player turn
        getMethod().invoke(game);

        // Next player turn
        getMethod().invoke(game);

        // Since he plays one card, he should draw two cards = 1 new card
        Assertions2.assertEquals(numberOfCards + 1, playerTwo.getHandSize(),
            context, result -> "The next player should draw two cards.");
    }

    @DisplayName("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt.")
    @Test
    void testDrawTwoLastTurnDrawTwo() throws Throwable {
        CardGamePlayer playerOne = CardGames.createPlayer("Lisa");
        CardGamePlayer playerTwo = CardGames.createPlayer("Jennie");
        CardGamePlayer playerThree = CardGames.createPlayer("Rosie");
        CardGamePlayer playerFour = CardGames.createPlayer("Jisoo");
        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);

        playerOne.takeCard(PlayingCard.DRAW_TWO);
        playerTwo.takeCard(PlayingCard.DRAW_TWO);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList)
            .flatMap(List::stream)
            .toList();

        CardGame game = H10_1_1_TestsPublic.CardGames.create(players, deck);

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Next player", playerTwo)
            .add("Player to draw cards", playerThree)
            .add("Playing card (first player)", PlayingCard.DRAW_TWO)
            .add("Playing card (second player)", PlayingCard.DRAW_TWO)
            .build();

        int numberOfCardsNext = playerTwo.getHandSize();
        int numberOfCardsThird = playerThree.getHandSize();

        // Current player turn
        getMethod().invoke(game);

        // Next player turn
        getMethod().invoke(game);

        // Third player turn
        getMethod().invoke(game);

        Assertions2.assertEquals(numberOfCardsNext - 1, playerTwo.getHandSize(), context,
            result -> "The next player should not draw any cards.");
        // Since he plays one card, he should draw four cards = 3 new card
        Assertions2.assertEquals(numberOfCardsThird + 3, playerThree.getHandSize(), context,
            result -> "The next player should draw two cards.");
    }

    @DisplayName("Wurden in den vorherigen Zügen mehrere DRAW_TWO-Karten gespielt, so erhöht sich die Anzahl der zu ziehenden Karten entsprechend.")
    @Test
    void testDrawTwoMultiple() throws Throwable {
        CardGamePlayer playerOne = CardGames.createPlayer("Lisa");
        CardGamePlayer playerTwo = CardGames.createPlayer("Jennie");
        CardGamePlayer playerThree = CardGames.createPlayer("Rosie");
        CardGamePlayer playerFour = CardGames.createPlayer("Jisoo");
        List<CardGamePlayer> players = List.of(playerOne, playerTwo, playerThree, playerFour);

        playerOne.takeCard(PlayingCard.DRAW_TWO);
        playerOne.takeCard(PlayingCard.SKIP);

        playerTwo.takeCard(PlayingCard.SKIP);
        playerTwo.takeCard(PlayingCard.DRAW_TWO);

        playerThree.takeCard(PlayingCard.DRAW_TWO);

        players.forEach(p -> {
            p.takeCard(PlayingCard.PASS);
            p.takeCard(PlayingCard.PASS);
        });

        List<PlayingCard> deck = players.stream()
            .map(player -> player.hand)
            .map(this::fromMyList)
            .flatMap(List::stream)
            .toList();

        CardGame game = H10_1_1_TestsPublic.CardGames.create(players, deck);

        int size = playerTwo.getHandSize();

        Context context = contextBuilder()
            .add("Players", players)
            .add("Deck", deck)
            .add("Current player", playerOne)
            .add("Next player", playerTwo)
            .add("Playing cards", List.of(PlayingCard.DRAW_TWO, PlayingCard.DRAW_TWO, PlayingCard.DRAW_TWO))
            .build();

        // Current player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(2, game.takeCards, context,
            result -> "The number of cards to draw should be 2.");

        // Next player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(4, game.takeCards, context,
            result -> "The number of cards to draw should be 4.");

        // Third player turn
        getMethod().invoke(game);
        Assertions2.assertEquals(6, game.takeCards, context,
            result -> "The number of cards to draw should be 6.");
    }

    @DisplayName("Der Spieler, der als letztes Karten in der Hand hat, wird korrekt bestimmt und zurückgegeben.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_3_3_Loser.json", customConverters = CUSTOM_CONVERTERS)
    void testLoser(JsonParameterSet parameters) throws Throwable {
        List<PlayingCard> deck = parameters.get("deck");
        List<CardGamePlayer> players = parameters.get("players");
        int loser = parameters.get("loser");
        CardGame game = H10_1_1_TestsPublic.CardGames.create(players, deck);

        MethodLink methodLink = Links.getMethod(getClassType(), "determineLoser");
        Context.Builder<?> builder = Assertions2.contextBuilder()
            .subject(methodLink.reflection())
            .add("Deck", deck);
        players.forEach(p -> builder.add(p.getName(), p.hand));
        Context context = builder.add("Loser", players.get(loser)).build();
        CardGamePlayer actualLoser = game.determineLoser();

        Assertions2.assertSame(
            players.get(loser),
            actualLoser,
            context,
            result -> "Loser mismatch."
        );
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
