package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import h10.util.ListItems;
import h10.util.MockDoubleLinkedList;
import h10.util.TestConstants;
import h10.util.TutorAssertionsPublic;
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
 * Tests for H10.2.4
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.4 | Ein Element entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_4_TestsPublic extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "input", node -> JsonConverters.toDoubleLinkedList(node, JsonNode::asInt),
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

    @DisplayName("Die Fälle 1 und 4 wurden korrekt implementiert.")
    @Test
    void testCase1() throws Throwable {
        ListItem<Integer> item = new ListItem<>(1);
        MockDoubleLinkedList<Integer> list = new MockDoubleLinkedList<>(item);
        Context context = contextBuilder()
            .add("List", list)
            .add("Element to remove", item)
            .add("List after removal", List.of())
            .build();
        int removed = getMethod().invoke(list, item);
        Assertions2.assertEquals(item.key, removed, context, result -> "Returned value should be the key of the removed item");
        Assertions2.assertNull(list.getHead(), context, result -> "Head should be null");
        Assertions2.assertNull(list.getTail(), context, result -> "Tail should be null");
    }

    @DisplayName("Die Fälle 1 und 4 wurden korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Case4.json", customConverters = CUSTOM_CONVERTERS)
    void testCase4(JsonParameterSet parameters) throws Throwable {
        MockDoubleLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.getHead()).toList();
        int key = parameters.get("key");
        ListItem<Integer> toRemove = items.stream().filter(item -> item.key.equals(key)).findFirst().orElseThrow();
        ListItem<Integer> prev = toRemove.prev;
        ListItem<Integer> next = toRemove.next;
        List<Integer> expected = parameters.get("expected");

        Context context = contextBuilder()
            .add("List", list)
            .add("Element to remove", toRemove)
            .add("List after removal", expected)
            .build();

        int removed = getMethod().invoke(list, toRemove);
        Assertions2.assertEquals(toRemove.key, removed, context, result -> "Returned value should be the key of the removed item");
        TutorAssertionsPublic.assertEquals(expected.iterator(), ListItems.iterator(list.getHead()), context);

        // Check references
        Assertions2.assertSame(next, prev.next, context, result -> "successor of the removed element != predecessor of the removed element.next");
        Assertions2.assertSame(prev, next.prev, context, result -> "predecessor of the removed element != successor of the removed element.prev");
    }

    @DisplayName("Das entfernte Element verweist immernoch auf seine Nachbarn.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_References.json", customConverters = CUSTOM_CONVERTERS)
    void testReference(JsonParameterSet parameters) throws Throwable {
        MockDoubleLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.getHead()).toList();
        int key = parameters.get("key");
        ListItem<Integer> toRemove = items.stream().filter(item -> item.key.equals(key)).findFirst().orElseThrow();
        ListItem<Integer> prev = toRemove.prev;
        ListItem<Integer> next = toRemove.next;

        Context context = contextBuilder()
            .add("List", list)
            .add("Element to remove", toRemove)
            .build();
        getMethod().invoke(list, toRemove);

        Assertions2.assertSame(prev, toRemove.prev, context, result -> "predecessor of the removed element should still point to the removed element");
        Assertions2.assertSame(next, toRemove.next, context, result -> "successor of the removed element should still point to the removed element");
    }
}
