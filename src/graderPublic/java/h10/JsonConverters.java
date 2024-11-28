package h10;

import com.fasterxml.jackson.databind.JsonNode;
import h10.assertions.Links;
import h10.util.ListItems;
import h10.util.MockList;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for working with JSON data.
 *
 * @author Nhan Huynh
 */
public final class JsonConverters extends org.tudalgo.algoutils.tutor.general.json.JsonConverters {

    /**
     * Prevent instantiation of this utility class.
     */
    private JsonConverters() {
    }

    /**
     * Converts the given JSON node to a list of items using the given mapper.
     *
     * @param node   the JSON node to convert
     * @param mapper the mapper to use for mapping the items
     * @param <T>    the type of the items in the list
     * @return the head of the list item created from the JSON node
     */
    public static <T> ListItem<T> toItems(JsonNode node, Function<JsonNode, T> mapper) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        return ListItems.toItems(toList(node, mapper));
    }

    /**
     * Converts the given JSON node to a playing card.
     *
     * @param node the JSON node to convert
     * @return the playing card represented by the JSON node
     */
    public static PlayingCard toPlayingCard(JsonNode node) {
        if (!node.isTextual()) {
            throw new IllegalArgumentException("Node is not a textual");
        }
        String value = node.asText().toUpperCase();
        return Arrays.stream(PlayingCard.values()).filter(card -> card.name().equals(value)).findFirst().orElseThrow();
    }

    /**
     * Converts the given JSON node to a deck of playing cards.
     *
     * @param node the JSON node to convert
     * @return the deck of playing cards represented by the JSON node
     */
    public static List<PlayingCard> toDeck(JsonNode node) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        return toList(node, JsonConverters::toPlayingCard);
    }

    /**
     * Converts the given JSON node to a player.
     *
     * @param node the JSON node to convert
     * @return the player represented by the JSON node
     */
    public static CardGamePlayer toPlayer(JsonNode node) {
        if (!node.isObject()) {
            throw new IllegalArgumentException("Node is not an object");
        }
        CardGamePlayer player = new CardGamePlayer(node.get("name").asText());
        FieldLink hand = Links.getField(CardGamePlayer.class, "hand");
        hand.set(player, MockList.of(toList(node.get("hand"), JsonConverters::toPlayingCard)));
        return player;
    }

    /**
     * Converts the given JSON node to a doubly linked list.
     *
     * @param node   the JSON node to convert
     * @param mapper the mapper to use for mapping the items
     * @param <T>    the type of the items in the list
     * @return the doubly linked list represented by the JSON nodeo
     */
    public static <T> DoublyLinkedList<T> toDoublyLinkedList(JsonNode node, Function<JsonNode, T> mapper) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        DoublyLinkedList<T> list = new DoublyLinkedList<>();
        list.head = toItems(node, mapper);
        list.tail = list.head;
        int size = 0;
        for (ListItem<T> current = list.head; current != null; current = current.next) {
            list.tail = current;
            size++;
        }
        list.size = size;
        return list;
    }
}
