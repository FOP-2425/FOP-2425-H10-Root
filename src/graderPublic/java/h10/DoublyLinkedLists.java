package h10;

import h10.util.ListItems;

import java.util.List;

/**
 * Utility class for doubly linked lists.
 *
 * @author Nhan Huynh
 */
public final class DoublyLinkedLists {

    /**
     * Prevent instantiation of this utility class.
     */
    private DoublyLinkedLists() {
    }

    /**
     * Creates a new doubly linked list from the given elements.
     *
     * @param elements the elements to create the list from
     * @param <T>      The type of the elements.
     * @return the new doubly linked list from the given elements
     */
    public static <T> DoublyLinkedList<T> create(List<T> elements) {
        ListItem<T> head = ListItems.toItems(elements);
        DoublyLinkedList<T> list = new DoublyLinkedList<>();
        list.head = head;
        list.tail = ListItems.itemStream(head).reduce((a, b) -> b).orElse(null);
        list.size = elements.size();
        return list;
    }
}
