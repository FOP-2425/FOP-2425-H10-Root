package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import h10.util.ListItems;
import h10.util.TutorAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for H10.2.2
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.2 | Auf ein Element in der Liste zugreifen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_2_TestsPrivate extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "list", node -> JsonConverters.toDoubleLinkedList(node, JsonNode::asInt),
            "index", JsonNode::asInt,
            "element", JsonNode::asInt,
            "begin", JsonNode::asBoolean
        )
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

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Path.json", customConverters = CUSTOM_CONVERTERS)
    void testPath(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("list");
        int index = parameters.get("index");
        boolean begin = parameters.get("begin");

        Context context = contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Search from", begin ? "beginning" : "end")
            .build();

        ListItems.itemStream(list.getHead()).forEach(item -> {
            if (begin) {
                item.prev = null;
            } else {
                item.next = null;
            }
        });

        Assertions2.call(() -> list.get(index), context, result -> "Search strategy mismatch");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Exception.json", customConverters = CUSTOM_CONVERTERS)
    void testException(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("list");
        int index = parameters.get("index");
        Context context = contextBuilder()
            .add("List", list)
            .add("Index", index)
            .build();

        Throwable throwable = Assertions2.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.get(index),
            context,
            result -> "Expected an IndexOutOfBoundsException to be thrown"
        );

        Assertions2.assertEquals("Index out of bounds", throwable.getMessage(), context, result -> "Exception message mismatch");
    }

    @Test
    void testRequirements() {
        TutorAssertions.assertIterative(getMethod(), getMethodName(), contextBuilder());
    }
}
