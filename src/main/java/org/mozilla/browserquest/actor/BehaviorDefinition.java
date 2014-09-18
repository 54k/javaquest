package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;

public class BehaviorDefinition {

    private Class<?> interfaceType;
    private Class<? extends Behavior> behavior;

    public BehaviorDefinition(Class<?> interfaceType, Class<? extends Behavior> behavior) {
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(behavior);
        validate(interfaceType, behavior);
        this.interfaceType = interfaceType;
        this.behavior = behavior;
    }

    static void validate(Class<?> behaviorType, Class<? extends Behavior> behavior) {
        Preconditions.checkArgument(behaviorType.isInterface());
        Preconditions.checkArgument(behaviorType.isAssignableFrom(behavior));
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public Class<? extends Behavior> getBehavior() {
        return behavior;
    }
}
