package h10;

import h10.rubric.H10_Tests;
import h10.util.ListItems;
import org.junit.jupiter.api.Assertions;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

/**
 * Defines the tests for {@code countSkipCardsXYZ} in the class {@code ListItemExamples}.
 *
 * <p>The class which implements this abstract class should override the method {@code testResult} and annotate it
 * with the {@code @ParameterizedTest} and {@code @JsonParameterSetTest} annotations.
 *
 * <p>Use the following schema:
 * <pre>{@code
 *      public class XYZ_Tests extends H10_1_CountSkipCards_Tests {
 *
 *          public static final Map<String, Function<JsonNode, ?>> CONVERTERS = Map.of(
 *              "deck", node -> ...,
 *               "skips", JsonNode::asInt
 *          );
 *
 *         protected abstract String getImplementationType() {
 *          return ...; // "Recursive" or "Iterative" or "Iterator"
 *         }
 *
 *         @ParameterizedTest
 *         @JsonParameterSetTest(value = "H10_1_CountSkipCards.json", customConverters = CUSTOM_CONVERTERS)
 *         @Override
 *         void testResult(JsonParameterSet parameters) throws Throwable {
 *         super.testResult(parameters);
 *         }
 *      }
 *  }</pre>
 *
 * @author Nhan Huynh
 */
public abstract class H10_1_CountSkipCards_Tests extends H10_Tests {


    @Override
    public Class<?> getClassType() {
        return ListItemExamples.class;
    }

    /**
     * Returns the implementation type for the method to test which is either recursive or iterative or using
     * iterators.
     *
     * @return the implementation type for the method to test
     */
    protected abstract String getImplementationType();

    @Override
    public String getMethodName() {
        if (!getImplementationType().equals("Recursive")
            && !getImplementationType().equals("Iterative")
            && !getImplementationType().equals("Iterator")
        ) {
            Assertions.fail("The implementation type must be either Recursive or Iterative or Iterator.");
        }

        return "countSkipCards" + getImplementationType();
    }

    /**
     * Asserts the result of the method.
     * @param parameters the parameters for the test method
     * @throws Throwable if an error occurs
     */
    void assertResult(JsonParameterSet parameters) throws Throwable {
        Object deck = parameters.get("deck");
        int expectedSkips = parameters.get("skips");
        Context context = contextBuilder()
            .add("Deck", (deck instanceof ListItem<?> items ? ListItems.stream(items).toList() : deck).toString())
            .add("Expected number of skips", expectedSkips)
            .build();

        MethodLink method = getMethod();
        int actualSkips = method.invokeStatic(deck);

        Assertions2.assertEquals(expectedSkips, actualSkips, context,
            result -> "The number of SKIP cards in the deck is incorrect.");
    }

    abstract void testResult(JsonParameterSet parameters) throws Throwable;
}
