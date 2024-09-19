package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Iterator;

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
    public T get(int index) {
        return getNode(index).key;
    }

    /**
     * Retrieves the node at the specified index in the doubly linked list.
     *
     * @param index the index of the node to retrieve
     * @return the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @StudentImplementationRequired
    private ListItem<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
            // Exceptions Teil von H08, hier nochmal fordern! VAnforderung!
            // Wird auch in VL07 gemacht!
        }

        // abuse double linked structure!! VAnforderung
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
        ListItem<T> tmp = new ListItem<T>();
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
            ListItem<T> p = getNode(index);

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
        ListItem<T> p = getNode(index);
        return removeListItem(p);
    }

    /**
     * Removes all occurrences of the specified element from the list, if present.
     *
     * @param key the element to be removed from the list, if present
     */
    @StudentImplementationRequired
    public void removeAll(T key) {
        // VAnforderung: Iterativ lösen
        for (ListItem<T> p = head; p != null; p = p.next) {
            if (p.key.equals(key)) {
                removeListItem(p);
            }
        }
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
        if (p == null) {
            return false;
        }
        if (p.key.equals(key)) {
            return true;
        }
        return containsHelper(p.next, key);
    }

    // Hier Konzept von Garbage Collector aufgreifen??
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // evtl. rausnehmen
    public void makeDistinct() {
        ListItem<T> p = head;
        while (p != null) {
            ListItem<T> q = p.next;
            while (q != null) {
                if (p.key.equals(q.key)) {
                    removeListItem(q);
                }
                q = q.next;
            }
            p = p.next;
        }
    }


    public class DoublyLinkedListIterator implements Iterator<T> {
        @DoNotTouch
        private ListItem<T> p;
        @DoNotTouch
        private int nextIndex = 0;

        @DoNotTouch
        public DoublyLinkedListIterator(ListItem<T> head) {
            this.p = head;
        }

        @Override
        @StudentImplementationRequired
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        @StudentImplementationRequired
        public T next() {
            T key = p.key;
            p = p.next;
            nextIndex++;
            return key;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public T previous() {
            if (p == null) {
                p = tail; // Jump back one ListItem
            } else {
                p = p.prev;
            }
            nextIndex--;
            return p.key;
        }

        @Override
        public void remove() {

        }
    }

    @DoNotTouch
    public DoublyLinkedListIterator iterator() {
        return new DoublyLinkedListIterator(head);
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
    }

    // Get cyclic iterator
    @DoNotTouch
    public CyclicDoublyLinkedListIterator cyclicIterator() {
        return new CyclicDoublyLinkedListIterator(head);
    }

    @Override
    @DoNotTouch
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
