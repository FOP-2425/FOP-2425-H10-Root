package h10.assertions;

import h10.ListItem;
import h10.util.ListItems;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Defines tutor-specific assertions for the h10 package.
 *
 * @author Nhan Huynh
 */
public final class TutorAssertions {

    /**
     * Prevent instantiation of this utility class.
     */
    private TutorAssertions() {
    }


    /**
     * Asserts that the given expected elements are equal to the actual elements.
     *
     * @param expected the expected elements
     * @param actual   the actual elements
     * @param mapper   the mapper to convert the actual elements to the expected elements
     * @param context  the context to use for the assertions
     * @param <T>      the type of the elements
     */
    public static <T> void assertEquals(Iterable<?> expected, ListItem<T> actual, Function<T, ?> mapper, Context context) {
        assertEquals(expected, () -> ListItems.stream(actual).map(mapper).map(it -> (Object) it).iterator(), context);
    }

    /**
     * Asserts that the given expected elements are equal to the actual elements.
     *
     * @param expected the expected elements
     * @param actual   the actual elements
     * @param context  the context to use for the assertions
     */
    public static void assertEquals(Iterable<?> expected, ListItem<?> actual, Context context) {
        assertEquals(expected, actual, Function.identity(), context);
    }

    /**
     * Asserts that the given expected elements are equal to the actual elements.
     *
     * @param expected the expected elements
     * @param actual   the actual elements
     * @param context  the context to use for the assertions
     */
    public static void assertEquals(Iterable<?> expected, Iterable<?> actual, Context context) {
        Iterator<?> itE = expected.iterator();
        Iterator<?> itA = actual.iterator();
        int index = 0;
        while (itE.hasNext() && itA.hasNext()) {
            int currentIndex = index;
            Assertions2.assertEquals(itE.next(), itA.next(), context,
                result -> "Element at index " + currentIndex + " does not match");
            index++;
        }

        // Check if expected or actual contains more elements
        int overflow = 0;
        while (itE.hasNext()) {
            itE.next();
            overflow++;
        }

        if (overflow > 0) {
            int currentOverflow = overflow;
            Assertions2.fail(context,
                result -> "Actual list has %s fewer elements".formatted(currentOverflow));
        }
        while (itA.hasNext()) {
            itA.next();
            overflow++;
        }
        if (overflow > 0) {
            int currentOverflow = overflow;
            Assertions2.fail(context,
                result -> "Actual list has %s more elements".formatted(currentOverflow));
        }
    }
}
