package h10;

import h10.util.ListItems;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.util.List;

/**
 * Tests the method {@code countSkipCardsXYZ} in the class {@code ListItemExamples}.
 */
public abstract class H10_1_CountSkipCards_Tests extends H10_Test {

    @Override
    public Class<?> getClassType() {
        return ListItemExamples.class;
    }

    public void testResult(JsonParameterSet parameters) throws Throwable {
        Object deck = parameters.get("deck");
        int expectedSkips = parameters.get("count");
        Context context = contextBuilder()
            .add("Deck", deck instanceof ListItem<?> items ? ListItems.stream(items).toList() : deck)
            .add("Number of skips", expectedSkips)
            .build();

        MethodLink method = getMethod();
        int actualSkips = method.invokeStatic(deck);

        Assertions2.assertEquals(expectedSkips, actualSkips, context, result -> "Number of skips is incorrect");
    }

    public abstract void testRequirements();
}
