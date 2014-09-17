package org.mozilla.browserquest.actor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

final class DefaultActorFactory {

    private static Map<Class<? extends Actor>, ActorDefinition> cache = new ConcurrentHashMap<>();

    private ClassPool classPool = ClassPool.getDefault();

    @SuppressWarnings("unchecked")
    public <T extends Actor> T newActor(Class<T> actorPrototype) {
        try {
            Objects.requireNonNull(actorPrototype);

            ActorDefinition actorDefinition;
            if (!cache.containsKey(actorPrototype)) {
                actorDefinition = getActorDefinition(actorPrototype);
                CtClass ctClass = classPool.makeClass(actorDefinition.getType().getCanonicalName() + "$" + UUID.randomUUID());
                ctClass.setSuperclass(asCtClass(actorPrototype));
                for (ProjectionDefinition projectionDefinition : actorDefinition.getProjectionDefinitions()) {
                    ctClass.addMethod(makeProjectionMethod(projectionDefinition, ctClass));
                }
                actorDefinition.setImplementation(ctClass.toClass());
                cache.put(actorPrototype, actorDefinition);
            } else {
                actorDefinition = cache.get(actorPrototype);
            }

            return newActor(actorDefinition);
        } catch (Throwable t) {
            throw new InstantiationException(t);
        }
    }

    private <T extends Actor> T newActor(ActorDefinition definition) {
        try {
            @SuppressWarnings("unchecked")
            T prototype = (T) definition.getImplementation().newInstance();
            prototype.setMetadata(BaseObjectMetadata.DEFINITION, definition);

            for (BehaviorDefinition behaviorDefinition : definition.getBehaviorDefinitions()) {
                Behavior behavior = behaviorDefinition.getBehavior().newInstance();
                prototype.addBehavior(behaviorDefinition.getInterfaceType(), behavior);
                for (Class subscription : behaviorDefinition.getSubscriptions()) {
                    prototype.addListener(subscription, behavior);
                }
            }
            return prototype;
        } catch (Throwable t) {
            throw new InstantiationException(t);
        }
    }

    private CtMethod makeProjectionMethod(ProjectionDefinition projectionDefinition, CtClass prototype) throws Exception {
        return CtNewMethod.make(asCtClass(projectionDefinition.getProjection()),
                projectionDefinition.getMethod().getName(),
                new CtClass[0], new CtClass[0],
                "return asBehavior(" + projectionDefinition.getProjection().getCanonicalName() + ".class);", prototype);
    }

    private static ActorDefinition getActorDefinition(Class<? extends Actor> actorPrototype) throws Exception {
        Collection<BehaviorDefinition> behaviorDefinitions = getBehaviorDefinitions(actorPrototype);
        Collection<ProjectionDefinition> projectionDefinitions = getProjectionDefinitions(actorPrototype);
        return new ActorDefinition(actorPrototype, behaviorDefinitions, projectionDefinitions);
    }

    private static Collection<BehaviorDefinition> getBehaviorDefinitions(Class<?> actorPrototype) throws Exception {
        if (!Actor.class.isAssignableFrom(actorPrototype)) {
            return Collections.emptyList();
        }

        Collection<BehaviorDefinition> behaviorDefinitions = new HashSet<>();

        for (Class<? extends Behavior> b :
                actorPrototype.getAnnotation(Actor.Prototype.class).value()) {
            Class<?> btype = getBehaviorType(b);
            behaviorDefinitions.add(new BehaviorDefinition(btype, b, getSubscriptions(b)));
        }
        behaviorDefinitions.addAll(getBehaviorDefinitions(actorPrototype.getSuperclass()));
        return behaviorDefinitions;
    }

    private static Class<?> getBehaviorType(Class<? extends Behavior> behavior) {
        return behavior.getAnnotation(Behavior.Prototype.class).value();
    }

    private static Collection<Class<?>> getSubscriptions(Class<? extends Behavior> behavior) {
        Collection<Class<?>> subscriptions = new HashSet<>();
        for (Class<?> aClass : behavior.getInterfaces()) {
            if (aClass.isAnnotationPresent(Actor.Listener.class)) {
                subscriptions.add(aClass);
            }
        }
        return subscriptions;
    }

    private static Collection<ProjectionDefinition> getProjectionDefinitions(Class<?> actorPrototype) throws Exception {
        if (!actorPrototype.isInterface() && !Actor.class.isAssignableFrom(actorPrototype)) {
            return Collections.emptyList();
        }

        Collection<ProjectionDefinition> projectionDefinitions = new HashSet<>();
        for (Method method : actorPrototype.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Actor.Projection.class)) {
                projectionDefinitions.add(new ProjectionDefinition(method, method.getReturnType()));
            }
        }
        for (Class<?> aClass : actorPrototype.getInterfaces()) {
            projectionDefinitions.addAll(getProjectionDefinitions(aClass));
        }
        if (!actorPrototype.isInterface()) {
            projectionDefinitions.addAll(getProjectionDefinitions(actorPrototype.getSuperclass()));
        }
        return projectionDefinitions;
    }

    private CtClass asCtClass(Class<?> clazz) throws Exception {
        return classPool.get(clazz.getName());
    }
}
