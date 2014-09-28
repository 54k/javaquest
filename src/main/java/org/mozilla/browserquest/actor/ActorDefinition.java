package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Collections;

public class ActorDefinition {

    private Class<? extends Actor> type;
    private Class<? extends Actor> implementation;

    private Collection<ComponentDefinition> componentDefinitions;
    private Collection<ViewDefinition> viewDefinitions;

    public ActorDefinition(Class<? extends Actor> type, Collection<ComponentDefinition> componentDefinitions, Collection<ViewDefinition> viewDefinitions) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(componentDefinitions);
        Preconditions.checkNotNull(viewDefinitions);
        this.type = type;
        this.componentDefinitions = componentDefinitions;
        this.viewDefinitions = viewDefinitions;
    }

    public Class<? extends Actor> getType() {
        return type;
    }

    public Class<? extends Actor> getImplementation() {
        return implementation;
    }

    public void setImplementation(Class<? extends Actor> implementation) {
        Preconditions.checkNotNull(implementation);
        this.implementation = implementation;
    }

    public Collection<ComponentDefinition> getComponentDefinitions() {
        return Collections.unmodifiableCollection(componentDefinitions);
    }

    public Collection<ViewDefinition> getViewDefinitions() {
        return Collections.unmodifiableCollection(viewDefinitions);
    }
}
