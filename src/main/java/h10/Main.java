package h10;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Manual testing
        CardGame game = CardGame.generateRandomCardGame();
        System.out.println(game);
        CardGamePlayer loser = game.determineLoser();
        System.out.println("The loser is: " + loser.getName());
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        List.of(1,2,3).forEach(list::add);
        BidirectionalIterator<Integer> iterator = list.cyclicIterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        iterator.remove();
       System.out.println(iterator.next());
    }
}
