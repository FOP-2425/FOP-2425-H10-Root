package h10;

import h10.assertions.TestConstants;
import h10.assertions.TutorAssertions;
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
 * Defines the private tests for H10.2.3.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.3 | Ein Element hinzufügen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_3_TestsPrivate extends H10_2_3_TestsPublic {

    @DisplayName("Fall 2: Neues Element an das Ende der Liste wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_End.json", customConverters = CUSTOM_CONVERTERS)
    void testEnd(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> items = ListItems.itemStream(list.head).toList();
        int element = parameters.get("key");
        List<Integer> expected = parameters.get("expected");
        int index = list.size();

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
        Assertions2.assertNull(list.tail.next, context, result -> "Successor of tail should be null.");
        Assertions2.assertSame(items.getLast(), list.tail.prev, context, result -> "oldTail != newTail.prev");
        Assertions2.assertSame(items.getLast().next, list.tail, context, result -> "oldTail.next != newTail");

        // Old reference to the old tail check
        // If the list is size < 2, the old tail predecessor should be null (head.prev = null)
        if (list.size > 2) {
            Assertions2.assertSame(items.getLast().prev.next, items.getLast(), context,
                result -> "oldTail.prev.next != oldTail");
        }
    }

    @DisplayName("Fall 4: Neues Element in der Mitte der Liste wurde korrekt implementiert.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Middle.json", customConverters = CUSTOM_CONVERTERS)
    void testMiddle(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        List<ListItem<Integer>> itemsBefore = ListItems.itemStream(list.head).toList();
        int index = parameters.get("index");
        int element = parameters.get("key");
        List<Integer> expected = parameters.get("expected");

        Context context = Assertions2.contextBuilder()
            .add("List", list.toString())
            .add("Index to add", index)
            .add("Element to add", element)
            .add("List after adding", expected.toString())
            .build();

        list.add(index, element);
        List<ListItem<Integer>> itemsAfter = ListItems.itemStream(list.head).toList();

        TutorAssertions.assertEquals(expected, list.head, context);

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
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        int element = parameters.get("key");
        int size = parameters.get("size");

        Context context = Assertions2.contextBuilder()
            .add("List", list.toString())
            .add("Index to add", index)
            .add("Element to add", element)
            .add("Size after adding", size)
            .build();

        list.add(index, element);
        Assertions2.assertEquals(size, list.size(), context, result -> "Size mismatch");
    }

    @DisplayName("Falls der übergebene key null ist, wird eine IllegalArgumentException geworfen.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Null.json", customConverters = CUSTOM_CONVERTERS)
    void testIllegalArgumentException(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");

        Context context = Assertions2.contextBuilder()
            .add("List", list)
            .add("Index to add", index)
            .add("Element to add", "null")
            .build();

        Assertions2.assertThrows(
            IllegalArgumentException.class,
            () -> list.add(index, null),
            context,
            result -> "Illegal argument exception should be thrown"
        );
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
