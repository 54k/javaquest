package org.mozilla.browserquest.actor;

import java.lang.reflect.Method;
import java.util.Objects;

public final class ProjectionDefinition {

    private Method method;
    private Class<?> projection;

    ProjectionDefinition(Method method, Class<?> projection) {
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
