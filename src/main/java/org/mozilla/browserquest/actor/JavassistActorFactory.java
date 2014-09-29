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
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JavassistActorFactory {

    private static Map<Class<? extends Actor>, ActorDefinition> cache = new ConcurrentHashMap<>();

    private ClassPool classPool = ClassPool.getDefault();

    @SuppressWarnings("unchecked")
    public <T extends Actor> T newActor(Class<T> actorPrototype) {
        try {
            Preconditions.checkNotNull(actorPrototype);

            ActorDefinition actorDefinition;
            if (!cache.containsKey(actorPrototype)) {
                actorDefinition = getActorDefinition(actorPrototype);
                CtClass ctClass = classPool.makeClass(actorDefinition.getType().getCanonicalName() + "Instance");
                ctClass.setSuperclass(asCtClass(actorPrototype));
                for (ViewDefinition viewDefinition : actorDefinition.getViewDefinitions()) {
                    ctClass.addMethod(makeViewMethod(viewDefinition, ctClass));
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

            for (ComponentDefinition componentDefinition : definition.getComponentDefinitions()) {
                Component component = componentDefinition.getBehavior().newInstance();
                prototype.addComponent(componentDefinition.getInterfaceType(), component);
                prototype.register(component);
            }
            return prototype;
        } catch (Throwable t) {
            throw new ActorInstantiationException(t);
        }
    }

    private CtMethod makeViewMethod(ViewDefinition viewDefinition, CtClass prototype) throws Exception {
        return CtNewMethod.make(asCtClass(viewDefinition.getProjection()), viewDefinition.getMethod().getName(), new CtClass[0], new CtClass[0],
                "return getComponent(" + viewDefinition.getProjection().getCanonicalName() + ".class);", prototype);
    }

    private static ActorDefinition getActorDefinition(Class<? extends Actor> actorPrototype) throws Exception {
        Collection<ComponentDefinition> componentDefinitions = getComponentDefinitions(actorPrototype);
        Collection<ViewDefinition> viewDefinitions = getProjectionDefinitions(actorPrototype);
        return new ActorDefinition(actorPrototype, componentDefinitions, viewDefinitions);
    }

    private static Collection<ComponentDefinition> getComponentDefinitions(Class<?> actorPrototype) throws Exception {
        if (!Actor.class.isAssignableFrom(actorPrototype)) {
            return Collections.emptyList();
        }

        Collection<ComponentDefinition> componentDefinitions = new HashSet<>();

        for (Class<? extends Component> impl : actorPrototype.getAnnotation(ActorPrototype.class).value()) {
            Class<?> type = getComponentType(impl);
            componentDefinitions.add(new ComponentDefinition(type, impl));
        }
        componentDefinitions.addAll(getComponentDefinitions(actorPrototype.getSuperclass()));
        return componentDefinitions;
    }

    private static Class<?> getComponentType(Class<? extends Component> component) {
        return component.getAnnotation(ComponentPrototype.class).value();
    }

    private static Collection<ViewDefinition> getProjectionDefinitions(Class<?> actorPrototype) throws Exception {
        if (!actorPrototype.isInterface() && !Actor.class.isAssignableFrom(actorPrototype)) {
            return Collections.emptyList();
        }

        Collection<ViewDefinition> viewDefinitions = new HashSet<>();

        for (Class<?> aClass : actorPrototype.getInterfaces()) {
            viewDefinitions.addAll(getProjectionDefinitions(aClass));
        }

        if (!actorPrototype.isInterface()) {
            viewDefinitions.addAll(getProjectionDefinitions(actorPrototype.getSuperclass()));
        } else if (actorPrototype.getAnnotation(ActorView.class) != null) {
            for (Method method : actorPrototype.getDeclaredMethods()) {
                viewDefinitions.add(new ViewDefinition(method, method.getReturnType()));
            }
        }

        return viewDefinitions;
    }

    private static Iterable<Class<?>> getActorSuperclasses(Class<? extends Actor> actorPrototype) {
        LinkedList<Class<?>> superClasses = new LinkedList<>();

        superClasses.addFirst(actorPrototype);

        Class<?> superClass = actorPrototype.getSuperclass();
        while (superClass.getAnnotation(ActorPrototype.class) != null) {
            superClasses.addFirst(superClass);
            superClass = superClass.getSuperclass();
        }

        return superClasses;
    }

    private CtClass asCtClass(Class<?> clazz) throws Exception {
        classPool.appendClassPath(new ClassClassPath(clazz));
        return classPool.get(clazz.getName());
    }
}
