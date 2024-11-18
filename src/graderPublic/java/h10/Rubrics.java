package h10;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;

import java.lang.reflect.Method;

public final class Rubrics {

    private Rubrics() {
        throw new IllegalStateException("Utility class");
    }

    public static Criterion.Builder criterionBuilder(String description, Grader grader) {
        return Criterion.builder()
            .shortDescription(description)
            .grader(grader);
    }

    public static Criterion.Builder criterionBuilder(String description, JUnitTestRef testRef) {
        return criterionBuilder(
            description,
            Grader.testAwareBuilder()
                .requirePass(testRef)
                .pointsFailedMin()
                .pointsPassedMax()
                .build()
        );
    }

    public static Criterion criterion(String description, int points,
                                      String className, String methodName, Class<?>... parametersType
    ) {
        Criterion.Builder builder;
        try {
            Method method = Class.forName(className).getDeclaredMethod(methodName, parametersType);
            builder = criterionBuilder(description, JUnitTestRef.ofMethod(method));
        } catch (Exception e) {
            builder = criterionBuilder(description, RubricUtils.graderPrivateOnly());
        }
        if (points >= 0) {
            builder.minPoints(0);
            builder.maxPoints(points);
        } else {
            builder.maxPoints(0);
            builder.minPoints(points);
        }
        return builder.build();
    }

    public static Criterion criterion(String description,
                                      String className, String methodName, Class<?>... parametersType) {
        return criterion(description, 1, className, methodName, parametersType);
    }
}
