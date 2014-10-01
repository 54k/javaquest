package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.util.TypedEventBus;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype
public abstract class Actor {

    private TypedEventBus typedEventBus = new TypedEventBus();
    private final Map<Class<?>, Component> componentsByType = new ConcurrentHashMap<>();

    public <T> T getComponent(Class<T> type) {
        Preconditions.checkNotNull(type);
        return (T) componentsByType.get(type);
    }

    public <T, C extends Component> void addComponent(Class<T> type, C component) {
        ComponentDefinition.validate(type, component.getClass());
        removeComponent(type);
        component.setActor(this);
        componentsByType.put(type, component);
    }

    public <T> T removeComponent(Class<T> type) {
        Preconditions.checkNotNull(type);
        Component component;
        if ((component = componentsByType.remove(type)) != null) {
            component.setActor(null);
            unregister(component);
        }
        return (T) component;
    }

    public Map<Class<?>, Component> getComponents() {
        return Collections.unmodifiableMap(componentsByType);
    }

    public boolean hasComponent(Class<?> type) {
        Preconditions.checkNotNull(type);
        return componentsByType.containsKey(type);
    }

    public <T> T post(Class<T> type) {
        return typedEventBus.post(type);
    }

    public void register(Object listener) {
        typedEventBus.register(listener);
    }

    public void register(Class<?> type, Object listener) {
        typedEventBus.register(type, listener);
    }

    public void unregister(Object listener) {
        typedEventBus.unregister(listener);
    }

    public void unregister(Class<?> type, Object listener) {
        typedEventBus.unregister(type, listener);
    }
}