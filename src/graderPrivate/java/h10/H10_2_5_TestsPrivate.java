package h10;

import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;

/**
 * Defines the private tests for H10.2.5.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.5 | Alle Elemente entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_5_TestsPrivate extends H10_2_4_TestsPublic {


    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
