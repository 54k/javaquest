package org.mozilla.browserquest.actor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Actor.Prototype
public abstract class Actor extends BaseObject {

    private static final DefaultActorFactory factory = new DefaultActorFactory();

    private final Map<Class<?>, Behavior> behaviors = new ConcurrentHashMap<>();

    public static <T extends Actor> T newActor(Class<T> prototype) {
        return factory.newActor(prototype);
    }

    @SuppressWarnings("unchecked")
    public <T> T asBehavior(Class<T> type) {
        Objects.requireNonNull(type);
        return (T) behaviors.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T, B extends Behavior> void addBehavior(Class<T> type, B behavior) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(behavior);
        BehaviorDefinition.validate(type, behavior.getClass());
        removeBehavior(type);
        behavior.setActor(this);
        behaviors.put(type, (Behavior) behavior.retain());
    }

    @SuppressWarnings("unchecked")
    public <T> T removeBehavior(Class<T> type) {
        Objects.requireNonNull(type);
        Behavior behavior;
        if ((behavior = behaviors.remove(type)) != null) {
            behavior.setActor(null);
            behavior.release();
        }
        return (T) behavior;
    }

    public boolean hasBehavior(Class<?> type) {
        Objects.requireNonNull(type);
        return behaviors.containsKey(type);
    }

    @Override
    public Actor retain() {
        return (Actor) super.retain();
    }

    @Override
    public Actor retain(int increment) {
        return (Actor) super.retain(increment);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Prototype {
        Class<? extends Behavior>[] value() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Listener {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Projection {
    }
}