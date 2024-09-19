package h10;

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(5);
        list.add(10);
        list.add(20);
        list.add(30, 1);

        System.out.println(list);
        // Iterator example
        DoublyLinkedList<Integer>.DoublyLinkedListIterator forwardIter = list.iterator();
        System.out.print("Forward iteration: ");
        while (forwardIter.hasNext()) {
            System.out.print(forwardIter.next() + " ");
        }
        System.out.println();

    }
}
