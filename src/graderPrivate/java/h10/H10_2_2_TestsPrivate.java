package h10;

import h10.assertions.TestConstants;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;

/**
 * Defines the private tests for H10.2.2.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.2 | Auf ein Element in der Liste zugreifen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_2_TestsPrivate extends H10_2_2_TestsPublic {

    @DisplayName("Die Suche wird in der Liste von vorne oder hinten gestartet, je nachdem, welcher Weg kürzer ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Path.json", customConverters = CUSTOM_CONVERTERS)
    void testPath(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        boolean searchFromStart = parameters.get("searchFromStart");

        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Index", index)
            .add("Search from", searchFromStart ? "start" : "end")
            .build();

        ListItems.itemStream(list.head).forEach(item -> {
            if (searchFromStart) {
                item.prev = null;
            } else {
                item.next = null;
            }
        });

        Assertions2.call(() -> list.get(index), context, result -> "Search strategy mismatch");
    }

    @DisplayName("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Exception.json", customConverters = CUSTOM_CONVERTERS)
    void testException(JsonParameterSet parameters) {
        DoublyLinkedList<Integer> list = parameters.get("input");
        int index = parameters.get("index");
        Context context = contextBuilder()
            .add("List", list.toString())
            .add("Index", index)
            .build();

        Assertions2.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.get(index),
            context,
            result -> "Expected an IndexOutOfBoundsException to be thrown"
        );
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion")
    @Test
    void testLoops() {
        Assertions4.assertIsNotRecursively(
            ((BasicMethodLink) getMethod()).getCtElement(),
            contextBuilder().build(),
            result -> "Method should not be recursive."
        );
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
