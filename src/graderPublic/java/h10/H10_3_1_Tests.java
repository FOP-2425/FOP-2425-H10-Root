package h10;

import h10.util.ListItems;
import h10.util.MockDoublyLinkedList;
import h10.util.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

/**
 * Tests for H10.3.1.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.3.1 | Das nächste Element zurückgeben")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_1_Tests extends H10_Test {

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
        testCases.forEach(list -> {
            MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(ListItems.toItems(list));
            BidirectionalIterator<Integer> it = mock.cyclicIterator();
            Context context = contextBuilder()
                .add("List", list)
                .add("Current element", list.isEmpty() ? "No elements left" : "null")
                .add("Next element", list.isEmpty() ? "No elements left" : list.getFirst())
                .build();
            if (list.isEmpty()) {
                Assertions2.assertFalse(it.hasNext(), context, result -> "hasNext() should return false if there are no elements left.");
            } else {
                Assertions2.assertTrue(it.hasNext(), context, result -> "hasNext() should return true if there are elements left.");
            }
        });
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextEmpty() {
        List<Integer> list = List.of(1, 2, 3);
        ListItem<Integer> items = ListItems.toItems(list);
        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", "null")
            .add("Next element", list.getFirst())
            .build();
        Assertions2.assertEquals(list.getFirst(), it.next(), context, result -> "next() should return the first element of the list.");
        Assertions2.assertEquals(items, it.p, context, result -> "p should point to the first element of the list.");
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextEnd() {
        List<Integer> list = List.of(1, 2, 3);
        ListItem<Integer> items = ListItems.toItems(list);
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        it.p = refs.getLast();
        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", list.getLast())
            .add("Next element", list.getFirst())
            .build();
        Assertions2.assertEquals(list.getFirst(), it.next(), context, result -> "next() should return the first element of the list.");
        Assertions2.assertEquals(items, it.p, context, result -> "p should point to the first element of the list.");
    }

    @DisplayName("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testNextMiddle() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        ListItem<Integer> items = ListItems.toItems(list);
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        int start = 1;
        int end = list.size() - 1;
        for (int i = start; i < end; i++) {
            it.p = refs.get(i - 1);
            Context context = contextBuilder()
                .add("List", list)
                .add("Current element", list.get(i - 1))
                .add("Next element", list.get(i))
                .build();
            Assertions2.assertEquals(list.get(i), it.next(), context, result -> "next() result mismatch");
            Assertions2.assertEquals(refs.get(i), it.p, context, result -> "p reference mismatch");
        }
    }

    @DisplayName("Die Methode next() setzt das Attribut calledRemove auf false.")
    @Test
    void testCalledRemove() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        ListItem<Integer> items = ListItems.toItems(list);

        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        it.p = items;
        it.calledRemove = true;

        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", list.getFirst())
            .add("Next element", list.get(1))
            .add("calledRemove", true)
            .build();
        it.next();
        Assertions.assertFalse(it.calledRemove, "calledRemove should be set to false after call");
    }

}
