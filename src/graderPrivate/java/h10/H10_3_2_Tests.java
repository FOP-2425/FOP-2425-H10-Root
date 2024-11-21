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
 * Tests for H10.3.2.
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.3.2 | Das vorherige Element zurückgeben")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_3_2_Tests extends H10_Test {

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
        testCases.forEach(list -> {
            MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(ListItems.toItems(list));
            BidirectionalIterator<Integer> it = mock.cyclicIterator();
            Context context = contextBuilder()
                .add("List", list)
                .add("Current element", list.isEmpty() ? "No elements left" : list.getFirst())
                .add("Previous element", list.isEmpty() ? "No elements left" : list.getLast())
                .build();
            if (list.isEmpty()) {
                Assertions2.assertFalse(it.hasPrevious(), context, result -> "hasPrevious() should return false if there are no elements left.");
            } else {
                Assertions2.assertTrue(it.hasPrevious(), context, result -> "hasPrevious() should return true if there are elements left.");
            }
        });
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testPreviousEmpty() {
        List<Integer> list = List.of(1, 2, 3);
        ListItem<Integer> items = ListItems.toItems(list);
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", "null")
            .add("Previous element", list.getLast())
            .build();
        Assertions2.assertEquals(list.getLast(), it.previous(), context, result -> "previous() should return the last element of the list.");
        Assertions2.assertEquals(refs.getLast(), it.p, context, result -> "p should point to the last element of the list.");
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
    @Test
    void testPreviousEnd() {
        List<Integer> list = List.of(1, 2, 3);
        ListItem<Integer> items = ListItems.toItems(list);
        List<ListItem<Integer>> refs = ListItems.itemStream(items).toList();
        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        it.p = refs.getFirst();
        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", list.getFirst())
            .add("Previous element", list.getLast())
            .build();
        Assertions2.assertEquals(list.getLast(), it.previous(), context, result -> "previous() should return the last element of the list.");
        Assertions2.assertEquals(refs.getLast(), it.p, context, result -> "p should point to the last element of the list.");
    }

    @DisplayName("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.")
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
            it.p = refs.get(i);
            Context context = contextBuilder()
                .add("List", list)
                .add("Current element", list.get(i))
                .add("Previous element", list.get(i - 1))
                .build();
            Assertions2.assertEquals(list.get(i - 1), it.previous(), context, result -> "previous() result mismatch");
            Assertions2.assertEquals(refs.get(i - 1), it.p, context, result -> "p reference mismatch");
        }
    }

    @DisplayName("Die Methode previous() setzt das Attribut calledRemove auf false.")
    @Test
    void testCalledRemove() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        ListItem<Integer> items = ListItems.toItems(list);

        MockDoublyLinkedList<Integer> mock = new MockDoublyLinkedList<>(items);
        DoublyLinkedList<Integer>.CyclicIterator it = (DoublyLinkedList<Integer>.CyclicIterator) mock.cyclicIterator();
        assert items != null;
        it.p = items.next;
        it.calledRemove = true;

        Context context = contextBuilder()
            .add("List", list)
            .add("Current element", list.get(1))
            .add("Previous element", list.getFirst())
            .add("calledRemove", true)
            .build();
        it.previous();
        Assertions.assertFalse(it.calledRemove, "calledRemove should be set to false after call");
    }

}
