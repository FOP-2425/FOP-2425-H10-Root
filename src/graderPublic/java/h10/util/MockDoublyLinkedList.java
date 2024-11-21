package h10.util;

import h10.DoublyLinkedList;
import h10.ListItem;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

public class MockDoublyLinkedList<T> extends DoublyLinkedList<T> {

    private final @NotNull FieldLink head;
    private final @NotNull FieldLink tail;
    private final @NotNull FieldLink size;

    public MockDoublyLinkedList(ListItem<T> items) {
        this.head = Links.getField(DoublyLinkedList.class, "head");
        this.tail = Links.getField(DoublyLinkedList.class, "tail");
        this.size = Links.getField(DoublyLinkedList.class, "size");
        setHead(items);
    }

    public ListItem<T> getHead() {
        return head.get(this);
    }

    public void setHead(ListItem<T> head) {
        this.head.set(this, head);
        ListItem<T> tail = null;
        int size = 0;
        for (ListItem<T> current = head; current != null; current = current.next) {
            tail = current;
            size++;
        }
        this.tail.set(this, tail);
        this.size.set(this, size);
    }

    public ListItem<T> getTail() {
        return tail.get(this);
    }
}
