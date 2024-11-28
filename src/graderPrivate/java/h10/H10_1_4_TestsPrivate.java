package h10;

import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;

/**
 * Defines the private tests for H10.1.4.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_4_TestsPrivate extends H10_1_4_TestsPublic {

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion")
    @Test
    void testLoops() {
        Assertions4.assertIsNotRecursively(
            ((BasicMethodLink) getMethod()).getCtElement(),
            contextBuilder().build(),
            result -> "Method should not be recursive."
        );
    }
}
