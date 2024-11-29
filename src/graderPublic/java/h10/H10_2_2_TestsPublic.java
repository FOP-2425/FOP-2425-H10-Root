package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.TestConstants;
import h10.rubric.H10_Tests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the public tests for H10.2.2.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.2 | Auf ein Element in der Liste zugreifen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_2_TestsPublic extends H10_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "input", node -> JsonConverters.toDoublyLinkedList(node, JsonNode::asInt),
        "index", JsonNode::asInt,
        "element", JsonNode::asInt,
        "searchFromStart", JsonNode::asBoolean
    );

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "getListItem";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(int.class);
    }

    @DisplayName("Die Methode gibt das Element an der angegebenen Position zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Position.json", customConverters = CUSTOM_CONVERTERS)
    void testPositions(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        int element = parameters.get("element");
        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Index", index)
            .add("Element to get", element)
            .build();
        int actual = list.get(index);
        Assertions2.assertEquals(element, actual, context, result -> "Element at the index %s mismatch".formatted(index));
    }
}
