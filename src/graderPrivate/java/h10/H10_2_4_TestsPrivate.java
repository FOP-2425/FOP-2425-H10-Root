package h10;

import h10.assertions.TestConstants;
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

/**
 * Defines the private tests for H10.2.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.4 | Ein Element entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_4_TestsPrivate extends H10_2_4_TestsPublic {

    @DisplayName("Der Fall 2 wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Case2.json", customConverters = CUSTOM_CONVERTERS)
    void testCase2(JsonParameterSet parameters) throws Throwable {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        ListItem<Integer> head = items.getFirst();
        ListItem<Integer> newHead = head.next;

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", head)
            .build();

        int removed = getMethod().invoke(list, head);

        // Check the return value
        Assertions2.assertEquals(head.key, removed, context,
            result -> "Returned value should be the key of the removed item");

        // Check references
        Assertions2.assertSame(newHead, list.head, context, result -> "New head mismatch.");
        Assertions2.assertNull(newHead.prev, context, result -> "New head should not have a predecessor.");
    }

    @DisplayName("Der Fall 3 wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Case3.json", customConverters = CUSTOM_CONVERTERS)
    void testCase3(JsonParameterSet parameters) throws Throwable {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        ListItem<Integer> newTail = items.get(items.size() - 2);
        ListItem<Integer> toRemove = items.getLast();
        int key = toRemove.key;

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", toRemove)
            .build();

        int removed = getMethod().invoke(list, toRemove);

        // Check the return value
        Assertions2.assertEquals(key, removed, context,
            result -> "Returned value should be the key of the removed item");

        // Check references
        Assertions2.assertSame(newTail, list.tail, context, result -> "New tail should be the last element.");
        Assertions2.assertNull(newTail.next, context, result -> "New tail should not have a successor.");
    }

    @DisplayName("Die Größe der Liste wird um 1 verringert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Size.json", customConverters = CUSTOM_CONVERTERS)
    void testSize(JsonParameterSet parameters) throws Throwable {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        int key = parameters.get("key");
        ListItem<Integer> toRemove = items.stream().filter(item -> item.key.equals(key)).findFirst().orElseThrow();
        int size = parameters.get("size");

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Element to remove", toRemove)
            .add("Size after removal", size)
            .build();

        getMethod().invoke(list, toRemove);

        Assertions2.assertEquals(size, list.size(), context, result -> "Size mismatch");
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
