package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.TestConstants;
import h10.assertions.TutorAssertions;
import h10.rubric.H10_Tests;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the public tests for H10.1.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.1 | Liste von Spielern erstellen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_1_TestsPublic extends H10_Tests {

    /**
     * The converters for the JSON parameters.
     */
    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "names", node -> JsonConverters.toList(node, JsonNode::asText)
    );

    @Override
    public Class<?> getClassType() {
        return ListItemExamples.class;
    }

    @Override
    public String getMethodName() {
        return "createPlayerListFromNames";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(String[].class);
    }

    @DisplayName("Die Liste wird korrekt erstellt und zurückgegeben. Jedes Listenelement verweist korrekt auf den vorherigen und nächsten Spieler, sofern dieser existiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_1_1.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) {
        List<String> expected = parameters.get("names");
        Context context = contextBuilder()
            .add("names", expected.toString())
            .add("Expected list", expected.toString())
            .build();

        // Method to test
        ListItem<CardGamePlayer> actual = ListItemExamples.createPlayerListFromNames(expected.toArray(String[]::new));

        // Check order of elements
        TutorAssertions.assertEquals(expected, actual, CardGamePlayer::getName, context);

        // Check references
        Iterator<String> expectedIterator = expected.iterator();
        Iterator<ListItem<CardGamePlayer>> playerIterator = ListItems.itemIterator(actual);
        ListItem<CardGamePlayer> previous = null;
        while (expectedIterator.hasNext() && playerIterator.hasNext()) {
            expectedIterator.next();
            ListItem<CardGamePlayer> actualPlayer = playerIterator.next();

            if (previous != null) {
                ListItem<CardGamePlayer> currentPrevious = previous;
                Assertions2.assertEquals(previous.next.key.getName(), actualPlayer.key.getName(), context,
                    result -> "The next player of %s should be %s."
                        .formatted(currentPrevious.key.getName(), actualPlayer.key.getName()));
            }
            previous = actualPlayer;
        }
    }
}
