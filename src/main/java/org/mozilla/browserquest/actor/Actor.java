package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.util.ListenersContainer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype
public abstract class Actor {

    private ListenersContainer listenersContainer = new ListenersContainer();
    private final Map<Class<?>, Behavior> behaviors = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getBehavior(Class<T> type) {
        Preconditions.checkNotNull(type);
        return (T) behaviors.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T, B extends Behavior> void addBehavior(Class<T> type, B behavior) {
        Preconditions.checkArgument(type.isInterface());
        BehaviorDefinition.validate(type, behavior.getClass());
        removeBehavior(type);
        behavior.setActor(this);
        behaviors.put(type, behavior);
    }

    @SuppressWarnings("unchecked")
    public <T> T removeBehavior(Class<T> type) {
        Preconditions.checkNotNull(type);
        Behavior behavior;
        if ((behavior = behaviors.remove(type)) != null) {
            behavior.setActor(null);
            unregister(behavior);
        }
        return (T) behavior;
    }

    public Map<Class<?>, Behavior> getBehaviors() {
        return Collections.unmodifiableMap(behaviors);
    }

    public boolean hasBehavior(Class<?> type) {
        Preconditions.checkNotNull(type);
        return behaviors.containsKey(type);
    }

    public <T> T post(Class<T> type) {
        return listenersContainer.post(type);
    }

    public void register(Object listener) {
        listenersContainer.register(listener);
    }

    public void unregister(Object listener) {
        listenersContainer.unregister(listener);
    }
}