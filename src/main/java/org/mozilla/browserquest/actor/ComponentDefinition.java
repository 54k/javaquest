package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;

public class ComponentDefinition {

    private Class<?> interfaceType;
    private Class<? extends Component> component;

    public ComponentDefinition(Class<?> interfaceType, Class<? extends Component> component) {
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(component);
        validate(interfaceType, component);
        this.interfaceType = interfaceType;
        this.component = component;
    }

    static void validate(Class<?> componentType, Class<? extends Component> component) {
        Preconditions.checkArgument(componentType.isInterface());
        Preconditions.checkArgument(componentType.isAssignableFrom(component));
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public Class<? extends Component> getComponent() {
        return component;
    }
}
