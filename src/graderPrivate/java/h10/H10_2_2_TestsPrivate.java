package h10;

import h10.util.ListItems;
import h10.util.MockDoublyLinkedList;
import h10.util.TestConstants;
import h10.util.TutorAssertionsPrivate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

/**
 * Tests for H10.2.2
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.2 | Auf ein Element in der Liste zugreifen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_2_TestsPrivate extends H10_2_2_TestsPublic {

    @DisplayName("Die Suche wird in der Liste von vorne oder hinten gestartet, je nachdem, welcher Weg kürzer ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_2_Path.json", customConverters = CUSTOM_CONVERTERS)
    void testPath(JsonParameterSet parameters) {
        MockDoublyLinkedList<Integer> list = parameters.get("list");
        int index = parameters.get("index");
        boolean begin = parameters.get("begin");

        Context context = contextBuilder()
            .add("List", list)
            .add("Index", index)
            .add("Search from", begin ? "beginning" : "end")
            .build();

        ListItems.itemStream(list.getHead()).forEach(item -> {
            if (begin) {
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
        MockDoublyLinkedList<Integer> list = parameters.get("list");
        int index = parameters.get("index");
        Context context = contextBuilder()
            .add("List", list)
            .add("Index", index)
            .build();

        Throwable throwable = Assertions2.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.get(index),
            context,
            result -> "Expected an IndexOutOfBoundsException to be thrown"
        );

        Assertions2.assertEquals("Index out of bounds", throwable.getMessage(), context, result -> "Exception message mismatch");
    }

    @DisplayName("Verbindliche Anforderung nicht erfüllt")
    @Test
    void testRequirements() {
        TutorAssertionsPrivate.assertIterative(getMethod(), getMethodName(), contextBuilder());
    }
}
