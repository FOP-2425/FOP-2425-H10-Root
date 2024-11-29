package h10.util;

import h10.ListItem;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;

/**
 * Utility class for working with {@link ListItem}s.
 *
 * @author Nhan Huynh
 */
public final class ListItems {

    /**
     * Prevent instantiation of this utility class.
     */
    private ListItems() {
    }

    /**
     * Returns an {@link Iterator} over the {@link ListItem}s in the list.
     *
     * @param head the head of the list
     * @param <T>  the type of the elements in the list
     * @return an iterator over the list
     */
    public static <T> Iterator<ListItem<T>> itemIterator(ListItem<T> head) {
        return itemStream(head).iterator();
    }

    /**
     * Returns an {@link Iterator} over the elements in the list.
     *
     * @param head the head of the list
     * @param <T>  the type of the elements in the list
     * @return an iterator over the elements in the list
     */
    public static <T> Iterator<T> iterator(ListItem<T> head) {
        return stream(head).iterator();
    }

    /**
     * Returns a {@link Stream} over the {@link ListItem}s in the list.
     *
     * @param head the head of the list
     * @param <T>  the type of the elements in the list
     * @return a stream over the list
     */
    public static <T> Stream<ListItem<T>> itemStream(ListItem<T> head) {
        return Stream.iterate(head, Objects::nonNull, item -> item.next);
    }

    /**
     * Returns a {@link Stream} over the elements in the list.
     *
     * @param head the head of the list
     * @param <T>  the type of the elements in the list
     * @return a stream over the elements in the list
     */
    public static <T> Stream<T> stream(ListItem<T> head) {
        return itemStream(head).map(item -> item.key);
    }

    @SafeVarargs
    public static <T> ListItem<T> of(T... elements) {
        return toItems(List.of(elements));
    }

    /**
     * Converts a list of elements to a linked list of {@link ListItem}s.
     *
     * @param elements the elements to convert
     * @param <T>      the type of the elements
     * @return the head of the list
     */
    public static <T> @Nullable ListItem<T> toItems(List<T> elements) {
        if (elements.isEmpty()) {
            return null;
        }
        List<ListItem<T>> items = elements.stream().map(ListItem::new).toList();
        items.stream().reduce((tail, item) -> {
            tail.next = item;
            item.prev = tail;
            return item;
        }).orElseThrow();
        return items.getFirst();
    }
}
