package h10;

import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;

/**
 * Defines the private tests for H10.1.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.1 | Liste von Spielern erstellen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_1_TestsPrivate extends H10_1_1_TestsPublic {

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
