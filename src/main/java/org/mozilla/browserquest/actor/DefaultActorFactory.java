package org.mozilla.browserquest.actor;

import com.google.common.base.Preconditions;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultActorFactory implements ActorFactory {

    private static Map<Class<? extends Actor>, ActorDefinition> cache = new ConcurrentHashMap<>();

    private ClassPool classPool = ClassPool.getDefault();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Actor> T newActor(Class<T> actorPrototype) {
        try {
            Preconditions.checkNotNull(actorPrototype);

            ActorDefinition actorDefinition;
            if (!cache.containsKey(actorPrototype)) {
                actorDefinition = getActorDefinition(actorPrototype);
                CtClass ctClass = classPool.makeClass(actorDefinition.getType().getCanonicalName() + "Instance");
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
            throw new ActorInstantiationException(t);
        }
    }

    private <T extends Actor> T newActor(ActorDefinition definition) {
        try {
            @SuppressWarnings("unchecked") T prototype = (T) definition.getImplementation().newInstance();

            for (BehaviorDefinition behaviorDefinition : definition.getBehaviorDefinitions()) {
                Behavior behavior = behaviorDefinition.getBehavior().newInstance();
                prototype.addBehavior(behaviorDefinition.getInterfaceType(), behavior);
                prototype.register(behavior);
            }
            return prototype;
        } catch (Throwable t) {
            throw new ActorInstantiationException(t);
        }
    }

    private CtMethod makeProjectionMethod(ProjectionDefinition projectionDefinition, CtClass prototype) throws Exception {
        return CtNewMethod.make(asCtClass(projectionDefinition.getProjection()), projectionDefinition.getMethod().getName(), new CtClass[0], new CtClass[0],
                "return getBehavior(" + projectionDefinition.getProjection().getCanonicalName() + ".class);", prototype);
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

        for (Class<? extends Behavior> impl : actorPrototype.getAnnotation(ActorPrototype.class).value()) {
            Class<?> type = getBehaviorType(impl);
            behaviorDefinitions.add(new BehaviorDefinition(type, impl));
        }
        behaviorDefinitions.addAll(getBehaviorDefinitions(actorPrototype.getSuperclass()));
        return behaviorDefinitions;
    }

    private static Class<?> getBehaviorType(Class<? extends Behavior> behavior) {
        return behavior.getAnnotation(BehaviorPrototype.class).value();
    }

    private static Collection<ProjectionDefinition> getProjectionDefinitions(Class<?> actorPrototype) throws Exception {
        if (!actorPrototype.isInterface() && !Actor.class.isAssignableFrom(actorPrototype)) {
            return Collections.emptyList();
        }

        Collection<ProjectionDefinition> projectionDefinitions = new HashSet<>();

        for (Class<?> aClass : actorPrototype.getInterfaces()) {
            projectionDefinitions.addAll(getProjectionDefinitions(aClass));
        }

        if (!actorPrototype.isInterface()) {
            projectionDefinitions.addAll(getProjectionDefinitions(actorPrototype.getSuperclass()));
        } else if (actorPrototype.getAnnotation(ActorProjection.class) != null) {
            for (Method method : actorPrototype.getDeclaredMethods()) {
                projectionDefinitions.add(new ProjectionDefinition(method, method.getReturnType()));
            }
        }

        return projectionDefinitions;
    }

    private CtClass asCtClass(Class<?> clazz) throws Exception {
        classPool.appendClassPath(new ClassClassPath(clazz));
        return classPool.get(clazz.getName());
    }
}
