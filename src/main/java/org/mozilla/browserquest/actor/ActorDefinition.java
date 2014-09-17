package org.mozilla.browserquest.actor;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class ActorDefinition extends BaseObjectDefinition<Actor> {

    private Class<? extends Actor> implementation;

    private Collection<BehaviorDefinition> behaviorDefinitions;
    private Collection<ProjectionDefinition> projectionDefinitions;

    ActorDefinition(Class<? extends Actor> type, Collection<BehaviorDefinition> behaviorDefinitions, Collection<ProjectionDefinition> projectionDefinitions) {
        super(type);
        Objects.requireNonNull(type);
        Objects.requireNonNull(behaviorDefinitions);
        Objects.requireNonNull(projectionDefinitions);
        this.behaviorDefinitions = behaviorDefinitions;
        this.projectionDefinitions = projectionDefinitions;
    }

    public Class<? extends Actor> getImplementation() {
        return implementation;
    }

    public void setImplementation(Class<? extends Actor> implementation) {
        Objects.requireNonNull(implementation);
        this.implementation = implementation;
    }

    public Collection<BehaviorDefinition> getBehaviorDefinitions() {
        return Collections.unmodifiableCollection(behaviorDefinitions);
    }

    public Collection<ProjectionDefinition> getProjectionDefinitions() {
        return Collections.unmodifiableCollection(projectionDefinitions);
    }
}
