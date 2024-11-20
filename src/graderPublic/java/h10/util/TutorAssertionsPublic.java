package h10.util;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Iterator;

/**
 * Utility class for custom assertions.
 *
 * @author Nhan huynh
 */
public final class TutorAssertionsPublic {

    /**
     * Prevent instantiation of this utility class.
     */
    private TutorAssertionsPublic() {
    }

    /**
     * Asserts that the given iterator contains the same elements.
     *
     * @param expected the expected iterator containing the expected elements
     * @param actual   the actual iterator containing the actual elements
     * @param context  the context information about the test
     */
    public static void assertEquals(Iterator<?> expected, Iterator<?> actual, Context context) {
        while (expected.hasNext() && actual.hasNext()) {
            Assertions2.assertEquals(expected.next(), actual.next(), context, result -> "Element mismatch");
        }
        Assertions2.assertFalse(expected.hasNext(), context, result -> "Expected list has more elements");
        Assertions2.assertFalse(actual.hasNext(), context, result -> "Actual list has more elements");
    }
}
