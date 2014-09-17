package org.mozilla.browserquest.actor;

import java.util.Collection;
import java.util.Objects;

public final class BehaviorDefinition {

    private Class<?> interfaceType;
    private Class<? extends Behavior> behavior;
    private Collection<Class<?>> subscriptions;

    BehaviorDefinition(Class<?> interfaceType, Class<? extends Behavior> behavior, Collection<Class<?>> subscriptions) {
        Objects.requireNonNull(interfaceType);
        Objects.requireNonNull(behavior);
        Objects.requireNonNull(subscriptions);
        validate(interfaceType, behavior);
        this.interfaceType = interfaceType;
        this.behavior = behavior;
        this.subscriptions = subscriptions;
    }

    static void validate(Class<?> behaviorType, Class<? extends Behavior> behavior) {
        if (!behaviorType.isAssignableFrom(behavior)) {
            throw new IllegalArgumentException(behavior.getName() + " is not assignable from " + behaviorType.getName());
        }
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public Class<? extends Behavior> getBehavior() {
        return behavior;
    }

    public Collection<Class<?>> getSubscriptions() {
        return subscriptions;
    }
}
