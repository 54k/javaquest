package org.mozilla.browserquest.util;

import com.google.common.base.Preconditions;
import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TypedEventBus {

    private final Map<Class<?>, Dispatcher> dispatcherByType = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T post(Class<T> type) {
        return (T) getDispatcherFor(type).proxy;
    }

    public void register(Object listener) {
        Class aClass = listener.getClass();
        for (Class<?> iClass : aClass.getInterfaces()) {
            if (iClass.getAnnotation(Listener.class) != null) {
                register(iClass, listener);
            }
        }
    }

    private void register(Class<?> type, Object listener) {
        validateType(type);
        getDispatcherFor(type).listeners.add(listener);
    }

    private static void validateType(Class<?> type) {
        for (Method method : type.getDeclaredMethods()) {
            Preconditions.checkArgument(method.getReturnType() == void.class);
        }
    }

    private Dispatcher getDispatcherFor(Class<?> type) {
        dispatcherByType.putIfAbsent(type, new Dispatcher(type));
        return dispatcherByType.get(type);
    }

    public void unregister(Object listener) {
        Class aClass = listener.getClass();
        for (Class<?> iClass : aClass.getInterfaces()) {
            if (iClass.getAnnotation(Listener.class) != null) {
                unregister(iClass, listener);
            }
        }
    }

    private void unregister(Class<?> type, Object listener) {
        getDispatcherFor(type).listeners.remove(listener);
    }

    private static final class Dispatcher implements InvocationHandler {

        final Object proxy;
        final Set<? super Object> listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

        Dispatcher(Class<?> type) {
            proxy = Reflection.newProxy(type, this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Object l : listeners) {
                method.invoke(l, args);
            }
            return null;
        }
    }

}
