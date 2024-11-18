package h10.util;

import h10.ListItem;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public final class ListItems {

    private ListItems() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Iterator<ListItem<T>> itemIterator(ListItem<T> head) {
        return new Iterator<>() {
            private ListItem<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListItem<T> next() {
                ListItem<T> result = current;
                current = current.next;
                return result;
            }
        };
    }

    public static <T> Iterator<T> iterator(ListItem<T> head) {
        return new Iterator<>() {
            final Iterator<ListItem<T>> itemIterator = itemIterator(head);

            @Override
            public boolean hasNext() {
                return itemIterator.hasNext();
            }

            @Override
            public T next() {
                return itemIterator.next().key;
            }
        };
    }

    public static <T> Stream<ListItem<T>> itemStream(ListItem<T> head) {
        return Stream.iterate(head, Objects::nonNull, item -> item.next);
    }

    public static <T> Stream<T> stream(ListItem<T> head) {
        return itemStream(head).map(item -> item.key);
    }
}
