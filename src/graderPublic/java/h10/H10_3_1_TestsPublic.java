package h10;

import h10.assertions.TestConstants;
import h10.rubric.H10_Tests;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

/**
 * Defines the public tests for H10.3.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.3.1 | Das nächste Element zurückgeben")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_1_TestsPublic extends H10_Tests {

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

    @DisplayName("Die Methode hasNext() gibt korrekt an, ob es ein nächstes Element gibt.")
    @Test
    void testHasNext() {
        List<List<Integer>> testCases = List.of(
            List.of(1, 2, 3),
            List.of(1),
            List.of()
        );
        testCases.forEach(elements -> {
            DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
            BidirectionalIterator<Integer> it = list.cyclicIterator();

            Context context = contextBuilder()
                .add("List", elements.toString())
                .add("Current element", elements.isEmpty() ? "No elements left" : "null")
                .add("Next element", elements.isEmpty() ? "No elements left" : elements.getFirst())
                .build();

            if (elements.isEmpty()) {
                Assertions2.assertFalse(it.hasNext(), context,
                    result -> "hasNext() should return false if there are no elements left.");
            } else {
                Assertions2.assertTrue(it.hasNext(), context,
                    result -> "hasNext() should return true if there are elements left.");
            }
        });
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextEmpty() {
        List<Integer> elements = List.of(1, 2, 3);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();

        Context context = contextBuilder()
            .add("List", elements.toString())
            .add("Current element", "null")
            .add("Next element", elements.getFirst())
            .build();

        Assertions2.assertEquals(elements.getFirst(), it.next(), context,
            result -> "next() should return the first element of the list.");
        Assertions2.assertEquals(items, it.p, context, result -> "p should point to the first element of the list.");
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextEnd() {
        List<Integer> elements = List.of(1, 2, 3);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();

        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        it.p = refs.getLast();

        Context context = contextBuilder()
            .add("List", elements.toString())
            .add("Current element", elements.getLast())
            .add("Next element", elements.getFirst())
            .build();

        Assertions2.assertEquals(elements.getFirst(), it.next(), context,
            result -> "next() should return the first element of the list.");
        Assertions2.assertEquals(items, it.p, context, result -> "p should point to the first element of the list.");
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextMiddle() {
        List<Integer> elements = List.of(1, 2, 3, 4, 5);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        int start = 1;
        int end = elements.size() - 1;
        for (int i = start; i < end; i++) {
            it.p = refs.get(i - 1);

            Context context = contextBuilder()
                .add("List", elements.toString())
                .add("Current element", elements.get(i - 1))
                .add("Next element", elements.get(i))
                .build();

            int currentIndex = i;
            Assertions2.assertEquals(elements.get(i), it.next(), context,
                result -> "next() result mismatch at index %s.".formatted(currentIndex));
            Assertions2.assertEquals(refs.get(i), it.p, context,
                result -> "p reference mismatch at index %s.".formatted(currentIndex));
        }
    }

    @DisplayName("Die Methode next() setzt das Attribut calledRemove auf false.")
    @Test
    void testCalledRemove() {
        List<Integer> elements = List.of(1, 2, 3, 4, 5);
        DoublyLinkedList<Integer> list = DoublyLinkedLists.create(elements);
        ListItem<Integer> items = list.head;
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) list.cyclicIterator();
        it.p = items;
        it.calledRemove = true;

        Context context = contextBuilder()
            .add("List", elements)
            .add("Current element", elements.getFirst())
            .add("Next element", elements.get(1))
            .add("calledRemove", true)
            .build();

        Assertions2.call(it::next, context, result -> "Expected next() to be called without exception.");
        Assertions2.assertFalse(it.calledRemove, context, result -> "calledRemove should be set to false after call.");
    }
}
