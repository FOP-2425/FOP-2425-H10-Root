package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.util.JsonConverters;
import h10.util.Links;
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
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Tests for H10.2.3.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.3 | Ein Element hinzufügen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_3_TestsPublic extends H10_Test {

    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "input", node -> JsonConverters.toDoubleLinkedList(node, JsonNode::asInt),
            "index", JsonNode::asInt,
            "key", JsonNode::asInt,
            "expected", node -> JsonConverters.toList(node, JsonNode::asInt)
        )
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

    @Test
    void testEmptyList() {
        MethodLink methodLink = Links.getMethod(
            getClassType(),
            getMethodName(),
            Matcher.of(method -> method.typeList().equals(List.of(BasicTypeLink.of(Object.class))))
        );
        MockDoubleLinkedList<Integer> list = new MockDoubleLinkedList<>(null);
        int element = 1;
        Context context = Assertions2.contextBuilder()
            .subject(methodLink)
            .add("List", list)
            .add("Element", element)
            .build();
        list.add(element);

        Assertions2.assertEquals(1, list.getHead().key, context, result -> "Head key mismatch");
        Assertions2.assertEquals(1, list.getTail().key, context, result -> "Tail key mismatch");
        Assertions2.assertSame(list.getHead(), list.getTail(), context, result -> "Head and tail should be the same");
        Assertions2.assertNull(list.getHead().prev, context, result -> "Head prev should be null");
        Assertions2.assertNull(list.getHead().next, context, result -> "Tail next should be null");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Start.json", customConverters = CUSTOM_CONVERTERS)
    void testStart(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.getHead()).toList();
        int element = parameters.get("key");
        List<Integer> expected = parameters.get("expected");
        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index", 0)
            .add("Element", element)
            .build();

        list.add(0, element);

        Iterator<Integer> expectedIterator = expected.iterator();
        Iterator<Integer> actualIterator = ListItems.iterator(list.getHead());
        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            Assertions2.assertEquals(expectedIterator.next(), actualIterator.next(), context, result -> "Element mismatch");
        }
        Assertions2.assertFalse(expectedIterator.hasNext(), context, result -> "Expected list has more elements");
        Assertions2.assertFalse(actualIterator.hasNext(), context, result -> "Actual list has more elements");

        // Check references
        Assertions2.assertEquals(list.getHead().next, items.get(0), context, result -> "Head next mismatch");
        Assertions2.assertEquals(list.getHead().prev, null, context, result -> "Head prev mismatch");
        Assertions2.assertEquals(items.get(0).prev, list.getHead(), context, result -> "First item prev mismatch");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Exception.json", customConverters = CUSTOM_CONVERTERS)
    void testException(JsonParameterSet parameters) {
        MockDoubleLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        int element = parameters.get("key");
        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Element", element)
            .build();

        Assertions2.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.add(index, element),
            context,
            result -> "Index out of bounds exception should be thrown"
        );
    }
}
