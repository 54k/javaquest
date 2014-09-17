package org.mozilla.browserquest.actor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Behavior<T extends Actor> extends BaseObject {

    private volatile T actor;

    public T getActor() {
        return actor;
    }

    void setActor(T actor) {
        this.actor = actor;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Prototype {
        Class<?> value() default Void.class;
    }
}
