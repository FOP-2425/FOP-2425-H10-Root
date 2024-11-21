package h10;

import h10.util.ListItems;
import h10.util.MockDoublyLinkedList;
import h10.util.TestConstants;
import h10.util.TutorAssertionsPublic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;

/**
 * Tests for H10.2.3.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.3 | Ein Element hinzufügen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_3_TestsPrivate extends H10_2_3_TestsPublic {

    @DisplayName("Fall 2: Neues Element an das Ende der Liste wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_End.json", customConverters = CUSTOM_CONVERTERS)
    void testEnd(JsonParameterSet parameters) {
        MockDoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.getHead()).toList();
        int element = parameters.get("key");
        int index = parameters.get("index");
        List<Integer> expected = parameters.get("expected");
        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Element to add", element)
            .add("List after adding", expected)
            .build();

        list.add(index, element);

        TutorAssertionsPublic.assertEquals(expected.iterator(), ListItems.iterator(list.getHead()), context);

        // Check references
        Assertions2.assertNull(list.getTail().next, context, result -> "Tail next should be null");
        Assertions2.assertSame(items.getLast(), list.getTail().prev, context, result -> "old last != tail.prev ");
        Assertions2.assertSame(items.getLast().next, list.getTail(), context, result -> "old last.next != tail");
        Assertions2.assertSame(items.getLast().prev.next, items.getLast(), context, result -> "old last.prev.next != old last");
    }

    @DisplayName("Fall 4: Neues Element in der Mitte der Liste wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Middle.json", customConverters = CUSTOM_CONVERTERS)
    void testMiddle(JsonParameterSet parameters) {
        MockDoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> itemsBefore = ListItems.itemStream(list.getHead()).toList();
        int index = parameters.get("index");
        int element = parameters.get("key");
        List<Integer> expected = parameters.get("expected");
        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Element to add", element)
            .add("List after adding", expected)
            .build();

        list.add(index, element);
        List<ListItem<Integer>> itemsAfter = ListItems.itemStream(list.getHead()).toList();

        TutorAssertionsPublic.assertEquals(expected.iterator(), ListItems.iterator(list.getHead()), context);

        // Check references

        ListItem<Integer> added = itemsAfter.get(index);
        ListItem<Integer> addedPrev = itemsBefore.get(index - 1);
        ListItem<Integer> addedNext = itemsBefore.get(index);
        Assertions2.assertEquals(added.key, element, context, result -> "Added element mismatch");

        Assertions2.assertSame(addedPrev.next, added, context, result -> "predecessor.next != added");
        Assertions2.assertSame(addedPrev, added.prev, context, result -> "predecessor != added.prev");
        Assertions2.assertSame(addedNext.prev, added, context, result -> "successor.prev != added");
        Assertions2.assertSame(addedNext, added.next, context, result -> "successor != added.next");
    }

    @DisplayName("Die Größe der Liste wird um 1 erhöht.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Size.json", customConverters = CUSTOM_CONVERTERS)
    void testSize(JsonParameterSet parameters) {
        MockDoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        int element = parameters.get("key");
        int size = parameters.get("size");
        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Element to add", element)
            .add("Index", index)
            .add("Size after adding", size)
            .build();

        list.add(index, element);
        Assertions2.assertEquals(size, list.size(), context, result -> "Size mismatch");
    }

    @DisplayName("Falls der übergebene key null ist, wird eine IllegalArgumentException geworfen.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Null.json", customConverters = CUSTOM_CONVERTERS)
    void testNull(JsonParameterSet parameters) {
        MockDoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");

        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Element to add", "null")
            .build();

        Throwable throwable = Assertions2.assertThrows(
            IllegalArgumentException.class,
            () -> list.add(index, null),
            context,
            result -> "Illegal argument exception should be thrown"
        );

        Assertions2.assertEquals("Key must not be null", throwable.getMessage(), context, result -> "Exception message mismatch");
    }
}
