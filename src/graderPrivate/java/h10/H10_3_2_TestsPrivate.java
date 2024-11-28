package h10;

import h10.assertions.TestConstants;
import h10.rubric.H10_Tests;
import h10.util.ListItems;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

/**
 * Defines the private tests for H10.3.2.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.3.2 | Das vorherige Element zurückgeben")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_2_TestsPrivate extends H10_Tests {

    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "cyclicIterator";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of();
    }

    @DisplayName("Die Methode hasPrevious() gibt korrekt an, ob es ein vorheriges Element gibt.")
    @Test
    void testHasPrevious() {
        List<List<Integer>> testCases = List.of(
            List.of(1, 2, 3),
            List.of(1),
            List.of()
        );
        testCases.forEach(elements -> {
            DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
            BidirectionalIterator<Integer> it = list.cyclicIterator();

            Context context = contextBuilder()
                .add("List", elements)
                .add("Current element", elements.isEmpty() ? "No elements left" : elements.getFirst())
                .add("Previous element", elements.isEmpty() ? "No elements left" : elements.getLast())
                .build();

            if (elements.isEmpty()) {
                Assertions2.assertFalse(it.hasPrevious(), context,
                    result -> "hasPrevious() should return false if there are no elements left.");
            } else {
                Assertions2.assertTrue(it.hasPrevious(), context,
                    result -> "hasPrevious() should return true if there are elements left.");
            }
        });
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testPreviousEmpty() {
        List<Integer> elements = List.of(1, 2, 3);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();

        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();

        Context context = contextBuilder()
            .add("List", elements)
            .add("Current element", "null")
            .add("Previous element", elements.getLast())
            .build();

        Assertions2.assertEquals(elements.getLast(), it.previous(), context,
            result -> "previous() should return the last element of the list.");
        Assertions2.assertEquals(refs.getLast(), it.p, context,
            result -> "p should point to the last element of the list.");
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testPreviousEnd() {
        List<Integer> elements = List.of(1, 2, 3);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();

        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        it.p = refs.getFirst();

        Context context = contextBuilder()
            .add("List", elements)
            .add("Current element", elements.getFirst())
            .add("Previous element", elements.getLast())
            .build();

        Assertions2.assertEquals(elements.getLast(), it.previous(), context,
            result -> "previous() should return the last element of the list.");
        Assertions2.assertEquals(refs.getLast(), it.p, context,
            result -> "p should point to the last element of the list.");
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextMiddle() {
        List<Integer> elements = List.of(1, 2, 3, 4, 5);
        ListItem<Integer> items = ListItems.toItems(elements);
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        int start = 1;
        int end = elements.size() - 1;
        for (int i = start; i < end; i++) {
            it.p = refs.get(i);

            Context context = contextBuilder()
                .add("List", elements)
                .add("Current element", elements.get(i))
                .add("Previous element", elements.get(i - 1))
                .build();

            Assertions2.assertEquals(elements.get(i - 1), it.previous(), context,
                result -> "previous() result mismatch");
            Assertions2.assertEquals(refs.get(i - 1), it.p, context, result -> "p reference mismatch");
        }
    }

    @DisplayName("Die Methode previous() setzt das Attribut calledRemove auf false.")
    @Test
    void testCalledRemove() {
        List<Integer> elements = List.of(1, 2, 3, 4, 5);
        ListItem<Integer> items = ListItems.toItems(elements);

        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        assert items != null;
        it.p = items.next;
        it.calledRemove = true;

        Context context = contextBuilder()
            .add("List", elements)
            .add("Current element", elements.get(1))
            .add("Previous element", elements.getFirst())
            .add("calledRemove", true)
            .build();
        it.previous();

        Assertions.assertFalse(it.calledRemove, "calledRemove should be set to false after call");
    }
}
