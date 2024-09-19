package h10;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {

    @Test
    public void addElementAtIndex() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 0);
        list.add(2, 1);
        list.add(3, 1);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    public void addElementAtInvalidIndex() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));
    }

    @Test
    public void removeElementByIndex() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 0);
        list.add(2, 1);
        list.add(3, 2);
        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    public void removeAllOccurrences() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 0);
        list.add(2, 1);
        list.add(1, 2);
        list.removeAll(1);
        assertEquals(1, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    public void containsElement() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 0);
        list.add(2, 1);
        assertTrue(list.contains(1));
        assertFalse(list.contains(3));
    }

    @Test
    public void cyclicIterator() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1, 0);
        list.add(2, 1);
        list.add(3, 2);
        Iterator<Integer> it = list.cyclicIterator();
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertEquals(1, it.next()); // Cyclic behavior
    }
}
