package h10;

import h10.assertions.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;

/**
 * Defines the private tests for H10.2.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.1 | Ist dieses Element bereits in der Liste?")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_1_TestsPrivate extends H10_2_1_TestsPublic {

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Schleifen")
    @Test
    void testRecursions() {
        Assertions4.assertIsNotIteratively(
            ((BasicMethodLink) getMethod()).getCtElement(),
            contextBuilder().build(),
            result -> "Method should not be iterative."
        );
    }

    @DisplayName("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen")
    @Test
    void testDataStructure() {
        TutorAssertionsPrivate.assertNoDataStructure(getMethod());
    }
}
