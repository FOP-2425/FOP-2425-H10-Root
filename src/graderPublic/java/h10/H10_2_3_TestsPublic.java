package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.TestConstants;
import h10.assertions.TutorAssertions;
import h10.rubric.H10_Tests;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
 * Defines the public tests for H10.2.3.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.3 | Ein Element hinzufügen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_3_TestsPublic extends H10_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
        "input", node -> JsonConverters.toDoublyLinkedList(node, JsonNode::asInt),
        "index", JsonNode::asInt,
        "key", JsonNode::asInt,
        "expected", node -> JsonConverters.toList(node, JsonNode::asInt),
        "size", JsonNode::asInt
    );

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "add";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(int.class, Object.class);
    }

    @DisplayName("Fall 1: Die Liste ist leer wurde korrekt implementiert.")
    @Test
    void testEmptyList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        int element = 1;

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to add", element)
            .add("List after adding", List.of(element))
            .build();
        list.add(element);

        Assertions2.assertEquals(1, list.head.key, context, result -> "Head key mismatch.");
        Assertions2.assertEquals(1, list.tail.key, context, result -> "Tail key mismatch.");
        Assertions2.assertSame(list.head, list.tail, context, result -> "Head and tail should be the same.");
        Assertions2.assertNull(list.head.prev, context, result -> "Head prev should be null.");
        Assertions2.assertNull(list.head.next, context, result -> "Tail next should be null.");
    }

    @DisplayName("Fall 3: Neues Element an den Anfang der Liste wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Start.json", customConverters = CUSTOM_CONVERTERS)
    void testStart(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        int element = parameters.get("key");
        List<Integer> expected = parameters.get("expected");
        int index = 0;

        Context context = Assertions2.contextBuilder()
            .add("List", list.toString())
            .add("Index to add", index)
            .add("Element to add", element)
            .add("List after adding", expected.toString())
            .build();

        list.add(index, element);

        // Check elements
        TutorAssertions.assertEquals(expected, list.head, context);

        // Check references
        Assertions2.assertSame(list.head.next, items.getFirst(), context, result -> "Head next mismatch.");
        Assertions2.assertNull(list.head.prev, context, result -> "Head does not have a previous item.");
        Assertions2.assertSame(items.getFirst().prev, list.head, context,
            result -> "The successor of the head does not have the head as the previous item.");
    }

    @DisplayName("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Exception.json", customConverters = CUSTOM_CONVERTERS)
    void testIndexOutOfBoundsException(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        int element = parameters.get("key");

        Context context = Assertions2.contextBuilder()
            .add("List", list.toString())
            .add("Index to add", index)
            .add("Element to add", element)
            .build();

        Assertions2.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.add(index, element),
            context,
            result -> "List with size %s should throw an index out of bounds exception for index %s."
                .formatted(list.size, index)
        );
    }
}
