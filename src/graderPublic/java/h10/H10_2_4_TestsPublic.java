package h10;

import h10.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;

/**
 * Tests for H10.2.4
 *
 * @author Nhan Huynh.
 */
@TestForSubmission
@DisplayName("H10.2.4 | Ein Element entfernen")
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H10_2_4_TestsPublic extends H10_Test {
    @Override
    public Class<?> getClassType() {
        return DoublyLinkedList.class;
    }

    @Override
    public String getMethodName() {
        return "removeListItem";
    }

    @Override
    public List<Class<?>> getMethodParameters() {
        return List.of(ListItem.class);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_4_Case1And3.json", customConverters = CUSTOM_CONVERTERS)
    void testCase1And4(JsonParameterSet paramters) {

    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H10_2_3_Exception.json", customConverters = CUSTOM_CONVERTERS)
    void testReference(JsonParameterSet paramters) {

    }
}
