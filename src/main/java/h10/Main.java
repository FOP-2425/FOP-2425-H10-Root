package h10;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        List<Integer> l1 = new LinkedList<>();

        l1.add(1);
        l1.add(2);
        l1.add(3);

        ListIterator<Integer> it = l1.listIterator();

        System.out.print(it.next() + " ");
        System.out.print(it.next() + " ");
        System.out.print(it.previous() + " ");
        System.out.print(it.previous() + " ");
        System.out.print(it.next() + " ");
        System.out.print(it.next() + " ");
        System.out.print(it.next() + " ");
        System.out.print(it.previous() + " ");
        System.out.print(it.previous() + " ");
        System.out.print(it.previous() + " ");
    }
}
