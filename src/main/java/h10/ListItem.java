package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class ListItem<T> {
    public T key;
    public ListItem<T> prev;
    public ListItem<T> next;
}
