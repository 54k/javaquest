package org.mozilla.browserquest.actor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Behavior<T extends Actor> {

    private volatile T actor;

    public T getActor() {
        return actor;
    }

    void setActor(T actor) {
        this.actor = actor;
    }

}
