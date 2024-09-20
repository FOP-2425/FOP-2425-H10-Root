package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Iterator;
import java.util.Objects;

public class DoublyLinkedList<T> {

    @DoNotTouch
    private ListItem<T> head;
    @DoNotTouch
    private ListItem<T> tail;
    @DoNotTouch
    private int size;

    @DoNotTouch
    public int size() {
        return size;
    }

    @DoNotTouch
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @DoNotTouch
    public boolean contains(T key) {
        // VAnforderung: Rekursiv lösen
        return containsHelper(head, key);
    }

    /**
     * Helper method to check if the list contains the specified element recursively.
     *
     * @param p the current node
     * @param key the element to be checked for presence in the list
     * @return true if the element is present, false otherwise
     */
    @StudentImplementationRequired
    private boolean containsHelper(ListItem<T> p, T key) {
        if (p == null) return false;
        if (Objects.equals(p.key, key)) return true;
        return containsHelper(p.next, key);
    }

    @DoNotTouch
    public T get(int index) {
        return getListItem(index).key;
    }

    /**
     * Retrieves the ListItem at the specified index in the doubly linked list.
     *
     * @param index the index of the node to retrieve
     * @return the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @StudentImplementationRequired
    private ListItem<T> getListItem(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        // Use double linked structure, iterate from the beginning or the end
        ListItem<T> p;
        if(index < (size / 2)) {
            p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            p = tail;
            for (int i = size - 1; i > index; i--) {
                p = p.prev;
            }
        }

        return p;
    }

    @DoNotTouch
    public void add(T key) {
        add(key, 0);
    }

    @StudentImplementationRequired
    public void add(T key, int index) {
        if(key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        ListItem<T> tmp = new ListItem<>();
        tmp.key = key;

        if (index == size) {
            if(head == null) { // List is empty
                head = tmp;
                tail = head;
            } else { // Append new element at the end of the list
                tail.next = tmp;
                tmp.prev = tail;
                tail = tmp;
            }
        } else {
            ListItem<T> p = getListItem(index);

            if (p == head) { // Append new element at the beginning of the list
                tmp.next = head;
                head.prev = tmp;
                head = tmp;
            } else { // Append new element in the middle of the list
                tmp.next = p;
                tmp.prev = p.prev;
                p.prev.next = tmp;
                p.prev = tmp;
            }
        }

        size++;
    }

    @StudentImplementationRequired
    private T removeListItem(ListItem<T> p) {
        if (p == head) {
            head = p.next;
        } else {
            p.prev.next = p.next;
        }

        if (p == tail) {
            tail = p.prev;
        } else {
            p.next.prev = p.prev;
        }

        size--;
        return p.key;
    }

    // Remove element by index
    @DoNotTouch
    public T remove(int index) {
        ListItem<T> p = getListItem(index);
        return removeListItem(p);
    }

    /**
     * Removes all occurrences of the specified element from the list, if present.
     *
     * @param key the element to be removed from the list, if present
     */
    @StudentImplementationRequired
    public void removeAll(T key) {
        for (ListItem<T> p = head; p != null; p = p.next) {
            if (Objects.equals(p.key, key)) {
                removeListItem(p);
            }
        }
    }




    // Hier Konzept von Garbage Collector aufgreifen??
    @StudentImplementationRequired
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // evtl. rausnehmen
    @StudentImplementationRequired
    public void makeDistinct() {
        ListItem<T> p = head;
        while (p != null) {
            ListItem<T> q = p.next;
            while (q != null) {
                if (Objects.equals(p.key, q.key)) {
                    removeListItem(q);
                }
                q = q.next;
            }
            p = p.next;
        }
    }

    /**
     * An iterator for traversing a doubly linked list in a cyclic manner.
     * This iterator wraps around to the head when it reaches the end of the list.
     */
    public class CyclicDoublyLinkedListIterator implements Iterator<T> {
        private ListItem<T> p;

        public CyclicDoublyLinkedListIterator(ListItem<T> head) {
            this.p = head;
        }

        @Override
        @StudentImplementationRequired
        public boolean hasNext() {
            return p != null; // Always true for cyclic iteration except if list is empty
        }

        @Override
        @StudentImplementationRequired
        public T next() {
            T data = p.key;
            p = p.next;
            if (p == null) {
                p = head; // Wrap around to the head
            }
            return data;
        }

        @StudentImplementationRequired
        public boolean hasPrevious() {
            return p != null; // Always true for cyclic iteration except if list is empty
        }

        @StudentImplementationRequired
        public T previous() {
            if(p == head) { // Beginning of list
                p = tail; // Wrap around to the tail
            } else if (p == null) { // End of list
                p = tail; // Jump back one ListItem
            } else {
                p = p.prev;
            }
            return p.key;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    // Get cyclic iterator
    @DoNotTouch
    public CyclicDoublyLinkedListIterator cyclicIterator() {
        return new CyclicDoublyLinkedListIterator(head);
    }

    /**
     * Returns a string representation of the doubly linked list.
     * The string representation consists of a list of the elements in the order they are stored,
     * enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    @Override
    @DoNotTouch
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (ListItem<T> p = head; p != null; p = p.next) {
            T e = p.key;
            sb.append(e);
            if (p.next == null) {
                break;
            }
            sb.append(',').append(' ');
        }
        return sb.append(']').toString();
    }
}
