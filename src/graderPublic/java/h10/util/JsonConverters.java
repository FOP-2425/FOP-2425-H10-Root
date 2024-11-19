package h10.util;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
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
     * Converts the given JSON node to a list of items using the given converter.
     *
     * @param node      the JSON node to convert
     * @param converter the converter to use for mapping the items
     * @param <T>       the type of the items in the list
     * @return the head of the list item created from the JSON node
     */
    public static <T> ListItem<T> toItems(JsonNode node, Function<JsonNode, T> converter) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        return ListItems.toItems(toList(node, converter));
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
}
