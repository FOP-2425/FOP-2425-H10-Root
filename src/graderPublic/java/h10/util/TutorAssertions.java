package h10.util;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLoop;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for custom assertions.
 *
 * @author Nhan huynh
 */
public final class TutorAssertions {

    /**
     * Prevent instantiation of this utility class.
     */
    private TutorAssertions() {
    }

    /**
     * Tests whether the given method is recursive.
     *
     * @param method     the method to test
     * @param methodName the name of the method
     * @param context    the context
     * @param skips      defines the methods to skip
     */
    @SafeVarargs
    public static void assertRecursive(
        MethodLink method,
        String methodName,
        Context.Builder<?> context,
        Matcher<MethodLink>... skips
    ) {
        var calls = getIterativeCalls(method, skips);
        var callsName = calls.stream().map(MethodLink::name).collect(Collectors.toSet());
        Assertions2.assertTrue(calls.isEmpty(), context.add("Method calls (iterative)", callsName).build(),
            result -> "The %s should be recursive, but found a loop(s) in %s".formatted(methodName, callsName)
        );
    }

    /**
     * Tests whether the given method is iterative.
     *
     * @param method     the method to test
     * @param methodName the name of the method
     * @param context    the context
     */
    @SafeVarargs
    public static void assertIterative(
        MethodLink method, String methodName,
        Context.Builder<?> context,
        Matcher<MethodLink>... skips
    ) {
        var calls = getRecursiveCalls(method, skips);
        var callsName = calls.stream().map(MethodLink::name).collect(Collectors.toSet());
        Assertions2.assertTrue(calls.isEmpty(), context.add("Method calls (recursive)", callsName).build(),
            result -> "The %s should be iterative, but found a recursion in %s".formatted(methodName, callsName)
        );
    }

    /**
     * Returns the method calls in the given method.
     *
     * @param method  the method to get the method calls for
     * @param visited the visited methods so far (to prevent infinite recursion)
     * @param skips   defines the methods to skip
     * @return the method calls in the given method
     */
    private static Set<MethodLink> getMethodCalls(
        MethodLink method,
        Set<MethodLink> visited,
        Matcher<MethodLink> skips
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
            .filter(methodLink -> skips.match(methodLink).isNegative())
            .collect(Collectors.toSet());
    }

    /**
     * Returns the recursive calls in the given method (including all method-calls in the method).
     *
     * @param method the method to get the recursive calls for
     * @param skips  defines the methods to skip
     * @return the recursive calls in the given method
     */
    @SafeVarargs
    public static Set<MethodLink> getRecursiveCalls(MethodLink method, Matcher<MethodLink>... skips) {
        Set<MethodLink> recursion = new HashSet<>();
        computeRecursiveCalls(method, recursion, new HashSet<>(), Arrays.stream(skips)
            .reduce(Matcher::or)
            .orElse(Matcher.never()));
        return recursion;
    }

    /**
     * Computes the recursive calls in the given method (including all method-calls in the method).
     *
     * @param method  the method to get the recursive calls for
     * @param found   the so far found recursive calls
     * @param visited the visited methods so far (to prevent infinite recursion)
     * @param skips   defines the methods to skip
     */
    private static void computeRecursiveCalls(
        MethodLink method, Set<MethodLink> found,
        Set<MethodLink> visited,
        Matcher<MethodLink> skips
    ) {
        var methodCalls = getMethodCalls(method, visited, skips);
        if (methodCalls.stream().anyMatch(m -> m.equals(method))) {
            found.add(method);
        }
        visited.addAll(methodCalls);
        for (MethodLink methodLink : methodCalls) {
            computeRecursiveCalls(methodLink, found, visited, skips);
        }
    }

    /**
     * Returns the iterative calls in the given method (including all method-calls in the method).
     *
     * @param method the method to get the iterative calls for
     * @param skips  defines the methods to skip
     * @return the iterative calls in the given method
     */
    @SafeVarargs
    public static Set<MethodLink> getIterativeCalls(MethodLink method, Matcher<MethodLink>... skips) {
        Set<MethodLink> recursion = new HashSet<>();
        computeIterativeCalls(method, recursion, new HashSet<>(), Arrays.stream(skips)
            .reduce(Matcher::or)
            .orElse(Matcher.never()));
        return recursion;
    }

    /**
     * Computes the iterative calls in the given method (including all method-calls in the method).
     *
     * @param method  the method to get the iterative calls for
     * @param found   the so far found iterative calls
     * @param visited the visited methods so far (to prevent infinite recursion)
     * @param skips   defines the methods to skip
     */
    private static void computeIterativeCalls(MethodLink method, Set<MethodLink> found, Set<MethodLink> visited, Matcher<MethodLink> skips) {
        CtMethod<?> ctMethod;
        try {
            ctMethod = ((BasicMethodLink) method).getCtElement();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            // java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
            // java.lang.NullPointerException: Cannot invoke "String.toCharArray()" because "this.content" is null
            // Occurs if we read src code from stdlib - skip them
            return;
        }
        if (!ctMethod.filterChildren(it -> it instanceof CtLoop).list().isEmpty()) {
            found.add(method);
        }
        var methodCalls = getMethodCalls(method, visited, skips);
        visited.addAll(methodCalls);
        for (MethodLink methodLink : methodCalls) {
            computeIterativeCalls(methodLink, found, visited, skips);
        }
    }

    /**
     * Returns {@code true} if the given method is recursive.
     *
     * @param method the method to test
     * @return {@code true} if the given method is recursive
     */
    public static boolean isRecursive(CtMethod<?> method) {
        List<? extends CtExecutableReference<?>> calls = method.filterChildren(it -> it instanceof CtInvocation<?>)
            .list()
            .stream()
            .map(it -> it instanceof CtInvocation<?> ? ((CtInvocation<?>) it).getExecutable() : null)
            .toList();
        if (!method.filterChildren(it -> it instanceof CtLoop).list().isEmpty()) {
            return false;
        }
        return isRecursive(method, calls, new HashSet<>(calls));
    }

    /**
     * Returns {@code true} if the given method is recursive.
     *
     * @param method  the method to test
     * @param calls   the calls of the method to test
     * @param visited the visited methods so far
     * @return {@code true} if the given method is recursive
     */
    private static boolean isRecursive(
        CtMethod<?> method,
        List<? extends CtExecutableReference<?>> calls,
        Set<CtExecutableReference<?>> visited
    ) {
        if (calls.stream().anyMatch(m -> m.getSimpleName().equals(method.getSimpleName()))) {
            return true;
        }
        for (CtExecutableReference<?> call : calls) {
            List<? extends CtExecutableReference<?>> newCalls = call.filterChildren(it -> it instanceof CtInvocation<?>)
                .list()
                .stream()
                .map(it -> it instanceof CtInvocation<?> ? ((CtInvocation<?>) it).getExecutable() : null)
                .filter(m -> !visited.contains(m))
                .peek(visited::add)
                .toList();
            if (newCalls.stream().anyMatch(m -> !m.filterChildren(statement -> statement instanceof CtLoop).list().isEmpty())) {
                return false;
            }
            return isRecursive(method, newCalls, visited);
        }
        return true;
    }
}
