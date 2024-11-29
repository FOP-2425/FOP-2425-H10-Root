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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines the public tests for H10.2.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.4 | Ein Element entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_4_TestsPublic extends H10_Tests {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "input", node -> JsonConverters.toDoublyLinkedList(node, JsonNode::asInt),
            "element", JsonNode::asInt,
            "expected", node -> JsonConverters.toList(node, JsonNode::asInt),
            "size", JsonNode::asInt
        )
    );

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "removeListItem";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(ListItem.class);
    }

    @DisplayName("Der Fall 1 wurde korrekt implementiert.")
    @Test
    void testCase1() throws Throwable {
        ListItem<Integer> item = new ListItem<>(1);
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.head = item;
        list.tail = item;
        list.size = 1;

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", item)
            .add("Expected list after removal", List.of())
            .build();

        int removed = getMethod().invoke(list, item);

        Assertions2.assertEquals(item.key, removed, context,
            result -> "Returned value should be the key of the removed item");
        Assertions2.assertNull(list.head, context, result -> "Head should be null");
        Assertions2.assertNull(list.tail, context, result -> "Tail should be null");
    }

    @DisplayName("Der Fall 4 wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Case4.json", customConverters = CUSTOM_CONVERTERS)
    void testCase4(JsonParameterSet parameters) throws Throwable {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        int key = parameters.get("key");
        ListItem<Integer> toRemove = items.stream().filter(item -> item.key.equals(key)).findFirst().orElseThrow();
        ListItem<Integer> prev = toRemove.prev;
        ListItem<Integer> next = toRemove.next;
        List<Integer> expected = parameters.get("expected");

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", toRemove)
            .add("Expected list after removal", expected.toString())
            .build();

        int removed = getMethod().invoke(list, toRemove);
        Assertions2.assertEquals(toRemove.key, removed, context,
            result -> "Returned value should be the key of the removed item");
        TutorAssertions.assertEquals(expected, list.head, context);

        // Check references
        Assertions2.assertSame(next, prev.next, context, result -> "removed.next != removed.prev.next");
        Assertions2.assertSame(prev, next.prev, context, result -> "removed.prev != removed.next.prev");
    }

    @DisplayName("Das entfernte Element verweist immernoch auf seine Nachbarn.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_References.json", customConverters = CUSTOM_CONVERTERS)
    void testReference(JsonParameterSet parameters) throws Throwable {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        int key = parameters.get("key");
        ListItem<Integer> toRemove = items.stream().filter(item -> item.key.equals(key)).findFirst().orElseThrow();
        ListItem<Integer> prev = toRemove.prev;
        ListItem<Integer> next = toRemove.next;

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", toRemove)
            .build();

        getMethod().invoke(list, toRemove);

        // Check references
        Assertions2.assertSame(prev, toRemove.prev, context, result -> "removed.prev != removed.prev");
        Assertions2.assertSame(next, toRemove.next, context, result -> "removed.next != removed.next");
    }
}
