package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import h10.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for H10.1.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_4_TestsPublic extends H10_1_CountSkipCards_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "deck", node -> JsonConverters.toList(node, JsonConverters::toPlayingCard),
            "count", JsonNode::asInt
        )
    );

    @Override
    public String getMethodName() {
        return "countSkipCardsIterator";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(List.class);
    }

    @DisplayName("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.")
    @Override
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_1_3.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) throws Throwable {
        super.testResult(parameters);
    }
}
