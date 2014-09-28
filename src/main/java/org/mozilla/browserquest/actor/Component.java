package org.mozilla.browserquest.actor;

public abstract class Component<T extends Actor> {

    private volatile T actor;

    public T getActor() {
        return actor;
    }

    void setActor(T actor) {
        this.actor = actor;
    }
}
