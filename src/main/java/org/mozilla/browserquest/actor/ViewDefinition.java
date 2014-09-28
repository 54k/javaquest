package org.mozilla.browserquest.actor;

import java.lang.reflect.Method;
import java.util.Objects;

public class ViewDefinition {

    private Method method;
    private Class<?> projection;

    public ViewDefinition(Method method, Class<?> projection) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(projection);
        this.method = method;
        this.projection = projection;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getProjection() {
        return projection;
    }
}
