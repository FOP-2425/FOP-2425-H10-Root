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
 * Defines the public tests for H10.2.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.1 | Ist dieses Element bereits in der Liste?")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_1_TestsPublic extends H10_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "list", node -> JsonConverters.toDoublyLinkedList(node, JsonNode::asInt),
        "key", JsonNode::asInt,
        "index", JsonNode::asInt
    );

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "findFirstHelper";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(ListItem.class, Object.class, int.class);
    }

    @DisplayName("Die Methode gibt den Index des ersten Vorkommens des Elements zurück, falls es in der Liste enthalten ist. Andernfalls wird -1 zurückgegeben.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_1.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("list");
        int key = parameters.get("key");
        int index = parameters.get("index");
        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Key", key)
            .add("Expected index", index)
            .build();
        int actual = list.findFirst(key);
        Assertions2.assertEquals(index, actual, context, result -> "Index of first occurrence mismatch.");
    }
}
