package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Collections;

public class ActorDefinition {

    private Class<? extends Actor> type;
    private Class<? extends Actor> implementation;

    private Collection<BehaviorDefinition> behaviorDefinitions;
    private Collection<ProjectionDefinition> projectionDefinitions;

    public ActorDefinition(Class<? extends Actor> type, Collection<BehaviorDefinition> behaviorDefinitions, Collection<ProjectionDefinition> projectionDefinitions) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(behaviorDefinitions);
        Preconditions.checkNotNull(projectionDefinitions);
        this.type = type;
        this.behaviorDefinitions = behaviorDefinitions;
        this.projectionDefinitions = projectionDefinitions;
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

    public Collection<BehaviorDefinition> getBehaviorDefinitions() {
        return Collections.unmodifiableCollection(behaviorDefinitions);
    }

    public Collection<ProjectionDefinition> getProjectionDefinitions() {
        return Collections.unmodifiableCollection(projectionDefinitions);
    }
}
