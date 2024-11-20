package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import h10.util.MockDoubleLinkedList;
import h10.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for H10.2.5.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.5 | Alle Elemente entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_5_Tests extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "input", node -> JsonConverters.toDoubleLinkedList(node, JsonNode::asInt)
    );

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "clear";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of();
    }

    @DisplayName("Nach einem Aufruf von clear() ist die Liste leer. Insbesondere sind head und tail auf null gesetzt, und die Größe der Liste ist 0.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_5.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("input");
        Context context = contextBuilder()
            .add("List", list)
            .add("List after clear()", List.of())
            .add("Size after clear()", 0)
            .build();

        list.clear();

        Assertions2.assertNull(list.getHead(), context, result -> "Head should be null after clear()");
        Assertions2.assertNull(list.getTail(), context, result -> "Tail should be null after clear()");
        Assertions2.assertEquals(0, list.size(), context, result -> "Size should be 0 after clear()");
    }
}
