package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface List<T> {

    /**
     * Checks if the list contains the specified element and returns the index of the first occurrence.
     *
     * @param key the element to be checked for presence in the list
     * @return the index of the element if the element is present, -1 otherwise
     */
    @DoNotTouch
    int findFirst(T key);

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @DoNotTouch
    int size();

    /**
     * Retrieves the element at the specified index in the doubly linked list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @DoNotTouch
    T get(int index);

    /**
     * Adds a new element at the end of the doubly linked list.
     *
     * @param key the element to be added
     * @throws IllegalArgumentException if the key is null
     */
    @DoNotTouch
    default void add(T key) {
        add(size(), key);
    }

    /**
     * Adds a new element at the specified index in the doubly linked list.
     *
     * @param index the position at which the element is to be added
     * @param key   the element to be added
     * @throws IllegalArgumentException  if the key is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @DoNotTouch
    void add(int index, T key);

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @DoNotTouch
    T removeAtIndex(int index);

    /**
     * Removes all elements from the list.
     */
    @DoNotTouch
    void clear();

    /**
     * Retrieve an iterator for traversing this doubly linked list in a cyclic manner.
     */
    @DoNotTouch
    public BidirectionalIterator<T> cyclicIterator();
}
