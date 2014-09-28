package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.util.ListenersContainer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype
public abstract class Actor {

    private ListenersContainer listenersContainer = new ListenersContainer();
    private final Map<Class<?>, Component> components = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        Preconditions.checkNotNull(type);
        return (T) components.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T, C extends Component> void addComponent(Class<T> type, C component) {
        Preconditions.checkArgument(type.isInterface());
        ComponentDefinition.validate(type, component.getClass());
        removeComponent(type);
        component.setActor(this);
        components.put(type, component);
    }

    @SuppressWarnings("unchecked")
    public <T> T removeComponent(Class<T> type) {
        Preconditions.checkNotNull(type);
        Component component;
        if ((component = components.remove(type)) != null) {
            component.setActor(null);
            unregister(component);
        }
        return (T) component;
    }

    public Map<Class<?>, Component> getComponents() {
        return Collections.unmodifiableMap(components);
    }

    public boolean hasComponent(Class<?> type) {
        Preconditions.checkNotNull(type);
        return components.containsKey(type);
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