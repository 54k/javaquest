package org.mozilla.browserquest.util;

import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListenersContainer {

    private final Map<Class<?>, Proxy> proxyByType = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T post(Class<T> type) {
        return (T) getProxyFor(type).proxy;
    }

    public void register(Object listener) {
        Class aClass = listener.getClass();
        for (Class<?> iClass : aClass.getInterfaces()) {
            if (iClass.getAnnotation(Listener.class) != null) {
                register(iClass, listener);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void register(Class type, Object listener) {
        getProxyFor(type).listeners.add(listener);
    }

    private Proxy getProxyFor(Class<?> type) {
        proxyByType.putIfAbsent(type, newProxy(type));
        return proxyByType.get(type);
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
        getProxyFor(type).listeners.remove(listener);
    }

    private static Proxy newProxy(Class<?> type) {
        Proxy proxy = new Proxy();
        Reflection.newProxy(type, proxy);
        proxy.proxy = Reflection.newProxy(type, proxy);
        return proxy;
    }

    private static final class Proxy implements InvocationHandler {

        private volatile Object proxy;
        private final Set listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Object l : listeners) {
                method.invoke(l, args);
            }
            return null;
        }
    }
}
