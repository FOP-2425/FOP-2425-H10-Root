package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator over elements that can be traversed in both directions.
 *
 * @param <T> the type of elements returned by this iterator
 */
@DoNotTouch
public interface BidirectionalIterator<T> extends Iterator<T> {

    /**
     * Returns {@code true} if this iterator has more elements when
     * traversing the list in the forward direction.
     *
     * @return {@code true} if the list iterator has more elements when
     * traversing the list in the forward direction
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration and advances the cursor position.
     * This method may be called repeatedly to iterate through the list,
     * or intermixed with calls to {@link #previous} to go back and forth.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if there are no more elements to iterate over
     */
    T next();


    /**
     * Returns {@code true} if this iterator has more elements when
     * traversing the list in the reverse direction.
     *
     * @return {@code true} if the iteration has more elements in the reverse direction
     */
    boolean hasPrevious();

    /**
     * Returns the previous element in the iteration and moves the cursor position backwards.
     * This method may be called repeatedly to iterate through the list,
     * or intermixed with calls to {@link #next} to go back and forth.
     *
     * @return the previous element in the iteration
     * @throws NoSuchElementException if there are no more elements to iterate over
     */
    T previous();

    /**
     * Removes the last element that was returned.
     *
     * @throws IllegalStateException if the `next` or `previous` method has not been called yet or the element has already been removed
     */
    void remove();
}
