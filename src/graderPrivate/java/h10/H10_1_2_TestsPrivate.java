package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the private tests for H10.1.2.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.2 | Vorkommen der Karte SKIP zählen - iterativ")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_2_TestsPrivate extends H10_1_CountSkipCards_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "deck", node -> JsonConverters.toItems(node, JsonConverters::toPlayingCard),
        "skips", JsonNode::asInt
    );

    @Override
    protected String getImplementationType() {
        return "Iterative";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(ListItem.class);
    }

    @DisplayName("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.")
    @Override
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_1_CountSkipCards.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) throws Throwable {
        assertResult(parameters);
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion")
    @Test
    void testLoops() {
        Assertions4.assertIsNotRecursively(
            ((BasicMethodLink) getMethod()).getCtElement(),
            contextBuilder().build(),
            result -> "Method should not be recursive."
        );
    }
}
