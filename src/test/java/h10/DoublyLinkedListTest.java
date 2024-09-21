package h10;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {
    @Test
    public void addElementAtBeginning() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0));
    }

    @Test
    public void addElementAtMiddle() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(3);
        list.add(2);
        list.add(1);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void removeAllOccurrences() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(3);
        list.add(1);
        list.add(1);
        list.add(2);
        list.removeAll(1);
        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    public void removeAllOccurrencesNotPresent() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(3);
        list.add(2);
        list.add(1);
        list.removeAll(4);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void clearList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(2);
        list.add(1);
        list.add(0);
        list.clear();
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    public void cyclicIteratorWrapAround() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(3);
        list.add(2);
        list.add(1);
        list.add(0);
        DoublyLinkedList<Integer>.CyclicDoublyLinkedListIterator it = list.cyclicIterator();
        assertEquals(0, it.next());
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next()); // Wrap around to the head
        assertEquals(0, it.next());
        assertEquals(1, it.next());

        assertEquals(1, it.previous());
        assertEquals(0, it.previous());
        assertEquals(3, it.previous());
        assertEquals(2, it.previous());
        assertEquals(1, it.previous());
        assertEquals(0, it.previous());
        assertEquals(3, it.previous());
    }

    @Test
    public void cyclicIteratorEmptyList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        Iterator<Integer> it = list.cyclicIterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void cyclicIteratorSingleElement() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        Iterator<Integer> it = list.cyclicIterator();
        assertEquals(1, it.next());
        assertEquals(1, it.next()); // Wrap around to the head
    }

    @Test
    public void cyclicIteratorRemoveElement() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(3);
        list.add(2);
        list.add(1);
        Iterator<Integer> it = list.cyclicIterator();
        assertEquals(1, it.next());
        it.remove();
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertEquals(2, it.next()); // Wrap around to the head
        assertEquals(3, it.next());
        it.remove();
        assertEquals(2, it.next()); // Wrap around to the head
        it.remove();
        assertFalse(it.hasNext());
    }
}
