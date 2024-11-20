package h10;

import h10.util.TutorAssertionsPrivate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;

/**
 * Tests for H10.2.1.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H10.2.1 | Ist dieses Element bereits in der Liste?")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_1_TestsPrivate extends H10_2_1_TestsPublic {

    @Test
    void testRequirements() {
        TutorAssertionsPrivate.assertRecursive(getMethod(), getMethodName(), contextBuilder());
    }
}
