package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;

public class ComponentDefinition {

    private Class<?> interfaceType;
    private Class<? extends Component> behavior;

    public ComponentDefinition(Class<?> interfaceType, Class<? extends Component> behavior) {
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(behavior);
        validate(interfaceType, behavior);
        this.interfaceType = interfaceType;
        this.behavior = behavior;
    }

    static void validate(Class<?> behaviorType, Class<? extends Component> behavior) {
        Preconditions.checkArgument(behaviorType.isInterface());
        Preconditions.checkArgument(behaviorType.isAssignableFrom(behavior));
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public Class<? extends Component> getBehavior() {
        return behavior;
    }
}
