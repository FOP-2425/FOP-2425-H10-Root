package h10;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import spoon.SpoonException;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.ImportScanner;
import spoon.reflect.visitor.ImportScannerImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class for assertions in the private tests of the tutor.
 *
 * @author Nhan Huynh
 */
public final class TutorAssertionsPrivate {

    /**
     * Blacklist of classes that are not allowed in the solution.
     */
    private static final Set<Class<?>> BLACKLIST_CLASSES = Set.of(
        Stream.class,
        ArrayList.class,
        LinkedList.class,
        Map.class,
        HashMap.class,
        Set.class
    );

    /**
     * Blacklist of calls that are not allowed in the solution.
     */
    private static final Set<String> BLACKLIST_CALLS = Set.of(
        "stream"
    );

    /**
     * Prevent instantiation of this utility class.
     */
    private TutorAssertionsPrivate() {

    }

    /**
     * Asserts that the given method link does not use any data structures.
     *
     * @param methodLink the method link to check
     */
    public static void assertNoDataStructure(MethodLink methodLink) {
        CtElement element = ((BasicMethodLink) methodLink).getCtElement();
        ImportScanner scanner = new ImportScannerImpl();
        scanner.computeImports(element);
        List<? extends Class<?>> classes = scanner.getAllImports()
            .stream()
            .map(CtElement::getReferencedTypes)
            .flatMap(Collection::stream)
            .map(ct -> {
                try {
                    return ct.getActualClass();
                } catch (SpoonException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toList();

        Context context = Assertions2.contextBuilder()
            .subject(methodLink.reflection())
            .add("Imported classes", classes)
            .build();
        Assertions2.assertFalse(classes.stream().anyMatch(BLACKLIST_CLASSES::contains), context,
            result -> "Data structures are not allowed in this task.");

        List<String> calls = element.filterChildren(e -> e instanceof CtInvocation<?>).map(Object::toString).list();
        Assertions2.assertFalse(calls.stream().anyMatch(BLACKLIST_CALLS::contains), context,
            result -> "Data structures are not allowed in this task.");
    }

}
