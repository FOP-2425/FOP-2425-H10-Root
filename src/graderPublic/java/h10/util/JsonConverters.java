package h10.util;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
import h10.MockDoubleLinkedList;
import h10.PlayingCard;

import java.util.Arrays;
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
     * Converts the given JSON node to a doubly linked list.
     *
     * @param node   the JSON node to convert
     * @param mapper the mapper to use for mapping the items
     * @param <T>    the type of the items in the list
     * @return the doubly linked list represented by the JSON node
     */
    public static <T> MockDoubleLinkedList<T> toDoubleLinkedList(JsonNode node, Function<JsonNode, T> mapper) {
        return new MockDoubleLinkedList<>(toItems(node, mapper));
    }
}
