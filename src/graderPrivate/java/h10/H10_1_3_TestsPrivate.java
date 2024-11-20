package h10;

import h10.util.TestConstants;
import h10.util.TutorAssertionsPrivate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;

/**
 * Tests for H10.1.3.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.1.3 | Vorkommen der Karte SKIP zählen - rekursiv")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_1_3_TestsPrivate extends H10_1_3_TestsPublic {

    @Test
    void testRequirements() {
        TutorAssertionsPrivate.assertRecursive(getMethod(), getMethodName(), contextBuilder());
    }
}
