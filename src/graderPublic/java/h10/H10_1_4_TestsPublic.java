package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the public tests for H10.1.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.3 | Vorkommen der Karte SKIP zählen - mit Iterator")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_4_TestsPublic extends H10_1_CountSkipCards_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "deck", node -> JsonConverters.toList(node, JsonConverters::toPlayingCard),
        "skips", JsonNode::asInt
    );

    @Override
    protected String getImplementationType() {
        return "Iterator";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(List.class);
    }

    @DisplayName("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.")
    @Override
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_1_CountSkipCards.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) throws Throwable {
        assertResult(parameters);
    }
}
