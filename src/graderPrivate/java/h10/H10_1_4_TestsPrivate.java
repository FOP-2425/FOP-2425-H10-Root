package h10;

import h10.util.TestConstants;
import h10.util.TutorAssertionsPrivate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tests for H10.1.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_4_TestsPrivate extends H10_1_4_TestsPublic {

    @DisplayName("Verbindliche Anforderung nicht erfüllt")
    @Test
    void testRequirements() {
        MethodLink method = getMethod();
        TutorAssertionsPrivate.assertIterative(method, getMethodName(), contextBuilder());
        Set<String> expected = Stream.of("hasNext", "next", "iterator").collect(Collectors.toSet());
        Set<String> actual = TutorAssertionsPrivate.getMethodCalls(method).stream().map(MethodLink::name)
            .filter(expected::contains).collect(Collectors.toSet());

        Context context = contextBuilder().add("Expected method calls", expected).build();

        Assertions2.assertEquals(expected, actual, context, result -> "Method calls are incorrect");
    }
}
