package h10;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import spoon.SpoonException;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.ImportScanner;
import spoon.reflect.visitor.ImportScannerImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
     * Set of method calls that are required for the solution with iterators.
     */
    private static final Set<MethodLink> ITERATOR_METHOD_CALLS;

    static {
        TypeLink iteratorType = BasicTypeLink.of(Iterator.class);
        MethodLink next = iteratorType.getMethod(Matcher.of(m -> m.name().equals("next")));
        MethodLink hasNext = iteratorType.getMethod(Matcher.of(m -> m.name().equals("hasNext")));
        TypeLink listType = BasicTypeLink.of(List.class);
        MethodLink iterator = listType.getMethod(Matcher.of(m -> m.name().equals("iterator")));
        ITERATOR_METHOD_CALLS = Set.of(next, hasNext, iterator);
    }

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

    /**
     * Lists all method calls in the given method.
     *
     * @param method  the method to check
     * @param visited the set of visited methods so far
     *
     * @return the set of method calls
     */
    private static Set<MethodLink> getMethodCalls(
            MethodLink method,
            Set<MethodLink> visited
    ) {
        CtMethod<?> ctMethod;
        try {
            ctMethod = ((BasicMethodLink) method).getCtElement();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            // java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
            // java.lang.NullPointerException: Cannot invoke "String.toCharArray()" because "this.content" is null
            // Occurs if we read src code from stdlib - skip them
            return Set.of();
        }
        return ctMethod.filterChildren(it -> it instanceof CtInvocation<?>)
                .list()
                .stream()
                .filter(element -> element instanceof CtInvocation<?> invocation)
                .map(element -> (CtInvocation<?>) element)
                .map(CtInvocation::getExecutable)
                .map(CtExecutableReference::getActualMethod)
                .filter(Objects::nonNull)
                .map(BasicMethodLink::of)
                .filter(methodLink -> !visited.contains(methodLink))
                .collect(Collectors.toSet());
    }

    /**
     * Asserts that the given method link uses an iterator.
     *
     * @param methodLink the method link to check
     */
    public static void assertIteratorUsed(MethodLink methodLink) {
        Set<MethodLink> methods = getMethodCalls(methodLink, new HashSet<>());
        Context context = Assertions2.contextBuilder()
                .subject(methodLink.reflection())
                .add("Method calls", methods)
                .build();

        Assertions2.assertTrue(methods.containsAll(ITERATOR_METHOD_CALLS), context,
                result -> "Iterator should be used in this task.");
    }
}
