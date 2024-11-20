package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
 * Tests for H10.2.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.1 | Ist dieses Element bereits in der Liste?")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_1_TestsPublic extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "list", node -> JsonConverters.toDoubleLinkedList(node, JsonNode::asInt),
            "key", JsonNode::asInt,
            "index", JsonNode::asInt
        )
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

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_1.json", customConverters = CUSTOM_CONVERTERS)
    void testResult(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("list");
        int key = parameters.get("key");
        int index = parameters.get("index");
        Context context = contextBuilder()
            .add("List", list)
            .add("Key", key)
            .add("Index", index)
            .build();
        int actual = list.findFirst(key);
        Assertions2.assertEquals(index, actual, context, result -> "Index of first occurrence mismatch");
    }

    @Test
    void testRequirements() {
        TutorAssertions.assertRecursive(getMethod(), getMethodName(), contextBuilder());
    }
}
