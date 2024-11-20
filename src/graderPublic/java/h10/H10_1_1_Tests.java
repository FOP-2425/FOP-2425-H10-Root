package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.ListItems;
import h10.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for H10.1.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.1 | Liste von Spielern erstellen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_1_Tests extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
            Map.of(
                    "data", node -> JsonConverters.toList(node, JsonNode::asText)
            )
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

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_1_1.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) {
        List<String> expected = parameters.get("data");
        Context context = contextBuilder()
                .add("names", expected)
                .build();
        ListItem<CardGamePlayer> actual = ListItemExamples.createPlayerListFromNames(expected.toArray(String[]::new));

        Iterator<String> expectedIterator = expected.iterator();
        Iterator<ListItem<CardGamePlayer>> playerIterator = ListItems.itemIterator(actual);

        ListItem<CardGamePlayer> previous = null;
        while (expectedIterator.hasNext() && playerIterator.hasNext()) {
            String expectedName = expectedIterator.next();
            ListItem<CardGamePlayer> actualPlayer = playerIterator.next();
            // Check if the player name is correct and in the correct order
            Assertions2.assertEquals(expectedName, actualPlayer.key.getName(), context, result -> "Player name does not match");

            // Check if the  players are correctly linked
            if (previous != null) {
                Assertions2.assertEquals(previous.next.key.getName(), actualPlayer.key.getName(), context, result -> "Player list is not linked correctly");
            }
            previous = actualPlayer;
        }
    }
}
