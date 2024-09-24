package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list implementation.
 *
 * @param <T> the type of elements stored in the list
 * @author Manuel Peters
 */
public class DoublyLinkedList<T> {

    /**
     * The head of the doubly linked list.
     * Points to the first element in the list.
     */
    @DoNotTouch
    private ListItem<T> head;

    /**
     * The tail of the doubly linked list.
     * Points to the last element in the list.
     */
    @DoNotTouch
    private ListItem<T> tail;

    /**
     * The size of the doubly linked list.
     */
    @DoNotTouch
    private int size;


    @DoNotTouch
    public int size() {
        return size;
    }

    /**
     * Constructs an empty doubly linked list.
     */
    @DoNotTouch
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param key the element to be checked for presence in the list
     * @return true if the element is present, false otherwise
     */
    @DoNotTouch
    public boolean contains(T key) {
        return containsHelper(head, key);
    }

    /**
     * Helper method to check if the list contains the specified element recursively.
     *
     * @param p the current ListItem
     * @param key the element to be checked for presence in the list
     * @return true if the element is present, false otherwise
     */
    @StudentImplementationRequired
    private boolean containsHelper(ListItem<T> p, T key) {
        if (p == null) return false;
        if (p.key.equals(key)) return true;
        return containsHelper(p.next, key);
    }

    /**
     * Retrieves the element at the specified index in the doubly linked list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @DoNotTouch
    public T get(int index) {
        return getListItem(index).key;
    }

    /**
     * Retrieves the ListItem at the specified index in the doubly linked list.
     *
     * @param index the index of the ListItem to retrieve
     * @return the ListItem at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
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

    /**
     * Adds a new element at the end of the doubly linked list.
     *
     * @param key the element to be added
     * @throws IllegalArgumentException if the key is null
     */
    @DoNotTouch
    public void add(T key) {
        add(size, key);
    }

    /**
     * Adds a new element at the specified index in the doubly linked list.
     *
     * @param index the position at which the element is to be added
     * @param key the element to be added
     * @throws IllegalArgumentException if the key is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @StudentImplementationRequired
    public void add(int index, T key) {
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

    /**
     * Removes the specified ListItem from the doubly linked list.
     *
     * @param p the ListItem to be removed
     * @return the key of the removed ListItem
     */
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

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @DoNotTouch
    public T removeAtIndex(int index) {
        ListItem<T> p = getListItem(index);
        return removeListItem(p);
    }

    /**
     * Removes all occurrences of the specified element from the list, if present.
     *
     * @param key the element to be completely removed from the list
     */
    @StudentImplementationRequired
    public void removeAll(T key) {
        for (ListItem<T> p = head; p != null; p = p.next) {
            if (p.key.equals(key)) {
                removeListItem(p);
            }
        }
    }

    /**
     * Removes all elements from the list.
     */
    @StudentImplementationRequired
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * An iterator for traversing a doubly linked list in a cyclic manner.
     */
    public class CyclicIterator implements Iterator<T> {
        /**
         * The current ListItem of the iterator in the doubly linked list.
         */
        @DoNotTouch
        private ListItem<T> p;
        @DoNotTouch
        private boolean calledRemove;

        @DoNotTouch
        public CyclicIterator() {
            this.p = null;
            this.calledRemove = false;
        }

        /**
         * Checks if there are more elements to iterate over in the list.
         *
         * @return true if there are more elements, false otherwise
         */
        @Override
        @StudentImplementationRequired
        public boolean hasNext() {
            return size > 0; // Always true for cyclic iteration except if list is empty
        }

        /**
         * Returns the next element in the list and advances the iterator.
         * If the end of the list is reached, it wraps around to the head.
         *
         * @return the next element in the list
         * @throws NoSuchElementException if there are no more elements to iterate over
         */
        @Override
        @StudentImplementationRequired
        public T next() {
            if(!hasNext()) throw new NoSuchElementException("The list is empty");

            if (p == null || p.next == null) {
                p = head;
            } else {
                p = p.next;
            }

            calledRemove = false;

            return p.key;
        }

        /**
         * Checks if there are previous elements to iterate over in the list.
         *
         * @return true if there are previous elements, false otherwise
         */
        @StudentImplementationRequired
        public boolean hasPrevious() {
            return size > 0; // Always true for cyclic iteration except if list is empty
        }

        /**
         * Returns the previous element in the list and moves the iterator backwards.
         * If the beginning of the list is reached, it wraps around to the tail.
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if there are no more elements to iterate over
         */
        @StudentImplementationRequired
        public T previous() {
            if(!hasPrevious()) throw new NoSuchElementException("The list is empty");

            if (p == null || p.prev == null) {
                p = tail;
            } else {
                p = p.prev;
            }

            calledRemove = false;

            return p.key;
        }

        /**
         * Removes the last element that was returned.
         *
         * @throws IllegalStateException if the `next` or `previous` method has not been called yet or the element has already been removed
         */
        @Override
        @StudentImplementationRequired
        public void remove() {
            if (p == null)  {
                throw new IllegalStateException("next or previous method has not been called yet");
            } else if (calledRemove) {
                throw new IllegalStateException("Element has already been removed");
            }

            removeListItem(p);
            calledRemove = true;
        }
    }

    /**
     * Retrieve an iterator for traversing this doubly linked list in a cyclic manner.
     */
    @DoNotTouch
    public CyclicIterator cyclicIterator() {
        return new CyclicIterator();
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
