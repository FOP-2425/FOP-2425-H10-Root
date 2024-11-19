package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a single item in a doubly linked list
 */
@DoNotTouch
public class ListItem<T> {

    /**
     * The key which this item hold.
     */
    public T key;

    /**
     * The reference to the previous item.
     */
    public ListItem<T> prev;

    /**
     * The reference to the next item.
     */
    public ListItem<T> next;

    /**
     * Creates a new single item in a doubly linked list with the given key, previous and next item.
     *
     * @param key  the key which this item should hold
     * @param prev the previous item in the list
     * @param next the next item in the list
     */
    public ListItem(T key, ListItem<T> prev, ListItem<T> next) {
        this.key = key;
        this.prev = prev;
        this.next = next;
    }

    /**
     * Creates a new single item in a doubly linked list with the given key.
     *
     * @param key the key which this item should hold
     */
    public ListItem(T key) {
        this(key, null, null);
    }

    /**
     * Creates a new single item in a doubly linked list with no key, previous or next item.
     */
    public ListItem() {
        this(null, null, null);
    }
}
