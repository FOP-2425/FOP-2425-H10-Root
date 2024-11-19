package h10.util;

import org.junit.jupiter.api.Test;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.match.MatcherFactories;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for links to classes and methods.
 *
 * @author Nhan Huynh
 */
public final class Links {

    /**
     * Matcher factory for string matchers.
     */
    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    /**
     * The package link for the h10 package.
     */
    public static final PackageLink PACKAGE = BasicPackageLink.of("h10");

    /**
     * Prevent instantiation of this utility class.
     */
    private Links() {

    }

    /**
     * Returns the method link for the given class and method name.
     *
     * @param clazz      the class to get the method from
     * @param methodName the name of the method
     * @param matchers   the matchers for additional checks to retrieve the method
     * @return the method link for the given class and method name
     */
    @SafeVarargs
    public static MethodLink getMethod(Class<?> clazz, String methodName, Matcher<MethodLink>... matchers) {
        return getMethod(getType(clazz), methodName, matchers);
    }

    /**
     * Returns the method link for the given type and method name.
     *
     * @param type       the type to get the method from
     * @param methodName the name of the method
     * @param matchers   the matchers for additional checks to retrieve the method
     * @return the method link for the given type and method name
     */
    @SafeVarargs
    public static MethodLink getMethod(TypeLink type, String methodName, Matcher<MethodLink>... matchers) {
        return Assertions3.assertMethodExists(
            type,
            Arrays.stream(matchers).reduce(STRING_MATCHER_FACTORY.matcher(methodName), Matcher::and)
        );
    }

    /**
     * Returns the type link for the given class.
     *
     * @param clazz the class to get the type from
     * @return the type link for the given class
     */
    public static TypeLink getType(Class<?> clazz) {
        return Assertions3.assertTypeExists(
            PACKAGE,
            STRING_MATCHER_FACTORY.matcher(clazz.getSimpleName())
        );
    }
}
