package org.mozilla.browserquest.actor;

class BaseObjectDefinition<T> {

    private final Class<? extends T> type;

    BaseObjectDefinition(Class<? extends T> type) {
        this.type = type;
    }

    public Class<? extends T> getType() {
        return type;
    }
}
