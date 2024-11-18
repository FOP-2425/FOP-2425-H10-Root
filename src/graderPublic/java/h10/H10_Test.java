package h10;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class H10_Test {

    public static final String CUSTOM_CONVERTERS = "CONVERTERS";

    private @Nullable TypeLink type;

    private @Nullable MethodLink method;

    @BeforeAll
    public void globalSetup() {
        this.type = Links.getType(getClassType());
        this.method = Links.getMethod(
            type,
            getMethodName(),
            Matcher.of(method -> method.typeList().equals(getMethodParametersLink()))
        );
    }

    public abstract Class<?> getClassType();

    public abstract String getMethodName();

    public abstract List<Class<?>> getMethodParameters();

    public List<TypeLink> getMethodParametersLink() {
        return getMethodParameters().stream().<TypeLink>map(BasicTypeLink::of).toList();
    }

    public TypeLink getType() {
        if (type == null) {
            throw new IllegalStateException("Type not initialized");
        }
        return type;
    }

    public MethodLink getMethod() {
        if (method == null) {
            throw new IllegalStateException("Method not initialized");
        }
        return method;
    }

    public Context.Builder<?> contextBuilder() {
        return Assertions2.contextBuilder().subject(getMethod());
    }
}
