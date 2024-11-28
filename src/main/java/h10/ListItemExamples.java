package h10;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Iterator;
import java.util.List;

public class ListItemExamples {

    /**
     * Creates a doubly linked list of CardGamePlayer objects from an array of names.
     * Each name in the array is used to create a CardGamePlayer object.
     * The list is doubly linked, with each ListItem pointing to the next and previous ListItem.
     *
     * @param names an array of names to create CardGamePlayer objects
     * @return the head of the doubly linked list of CardGamePlayer objects
     */
    @StudentImplementationRequired
    public static ListItem<CardGamePlayer> createPlayerListFromNames(String[] names) {
        ListItem<CardGamePlayer> playerList = null;
        ListItem<CardGamePlayer> current = null;

        for (String name : names) {
            ListItem<CardGamePlayer> newItem = new ListItem<>();
            newItem.key = new CardGamePlayer(name);
            if (playerList == null) {
                playerList = newItem;
            } else {
                current.next = newItem;
                newItem.prev = current;
            }
            current = newItem;
        }

        return playerList;
    }

    /**
     * Counts the number of SKIP cards in a doubly linked list of PlayingCard objects iteratively.
     *
     * @param cardDeck the head of the doubly linked list of PlayingCard objects
     * @return the number of SKIP cards in the list
     */
    @StudentImplementationRequired
    public static int countSkipCardsIterative(ListItem<PlayingCard> cardDeck) {
        int count = 0;

        for (ListItem<PlayingCard> p = cardDeck; p != null; p = p.next) {
            if (PlayingCard.SKIP.equals(p.key)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Counts the number of SKIP cards in a doubly linked list of PlayingCard objects recursively.
     *
     * @param cardDeck the head of the doubly linked list of PlayingCard objects
     * @return the number of SKIP cards in the list
     */
    @StudentImplementationRequired
    public static int countSkipCardsRecursive(ListItem<PlayingCard> cardDeck) {
        if (cardDeck == null) {
            return 0;
        }
        int count = PlayingCard.SKIP.equals(cardDeck.key) ? 1 : 0;
        return count + countSkipCardsRecursive(cardDeck.next);
    }

    /**
     * Counts the number of SKIP cards in a list of PlayingCard objects using an iterator.
     *
     * @param cardDeck the list of PlayingCard objects
     * @return the number of SKIP cards in the list
     */
    @StudentImplementationRequired
    @SuppressWarnings({"WhileLoopReplaceableByForEach", "ForLoopReplaceableByForEach"})
    public static int countSkipCardsIterator(List<PlayingCard> cardDeck) {
        int count = 0;

        Iterator<PlayingCard> iter = cardDeck.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(PlayingCard.SKIP)) {
                count++;
            }
        }

        return count;
    }

}
