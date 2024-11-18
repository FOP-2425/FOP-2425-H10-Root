package h10;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.match.MatcherFactories;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;

public final class Links {

    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    public static final PackageLink PACKAGE = BasicPackageLink.of("h10");

    private Links() {

    }

    @SafeVarargs
    public static MethodLink getMethod(Class<?> clazz, String methodName, Matcher<MethodLink>... matchers) {
        return getMethod(getType(clazz), methodName, matchers);
    }

    @SafeVarargs
    public static MethodLink getMethod(TypeLink type, String methodName, Matcher<MethodLink>... matchers) {
        return Assertions3.assertMethodExists(
            type,
            Arrays.stream(matchers).reduce(STRING_MATCHER_FACTORY.matcher(methodName), Matcher::and)
        );
    }

    public static TypeLink getType(Class<?> clazz) {
        return Assertions3.assertTypeExists(
            PACKAGE,
            STRING_MATCHER_FACTORY.matcher(clazz.getSimpleName())
        );
    }
}
