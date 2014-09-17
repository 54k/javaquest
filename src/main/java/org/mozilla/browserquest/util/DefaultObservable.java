package org.mozilla.browserquest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultObservable implements Observable {

    private static Logger logger = LoggerFactory.getLogger(DefaultObservable.class);
    private static final DispatcherProxy<?> EMPTY_DISPATCHER = new DispatcherProxy();

    private final Map<Class<?>, DispatcherProxy> listeners = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T notify(Class<T> type) {
        Objects.requireNonNull(type);
        return dispatcherFor(type).proxy();
    }

    @Override
    public <T> void addListener(Class<T> type, T listener) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(listener);
        dispatcherFor(type).addListener(listener);
    }

    @SuppressWarnings("unchecked")
    private <T> DispatcherProxy<T> dispatcherFor(Class<T> type) {
        if (listeners.containsKey(type)) {
            return (DispatcherProxy<T>) listeners.get(type);
        }

        DispatcherProxy<T> dispatcherProxy = DispatcherProxy.newDispatcher(type);
        listeners.put(type, dispatcherProxy);
        return dispatcherProxy;
    }

    @Override
    public <T> void removeListener(Class<T> type, T listener) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(listener);
        dispatcherFor(type).removeListener(listener);
    }

    @Override
    public void dropListener(Object listener) {
        Objects.requireNonNull(listener);
        listeners.values().forEach((a) -> a.removeListener(listener));
    }

    @Override
    public void dropListeners() {
        listeners.clear();
    }

    @Override
    public boolean hasListeners(Class<?> type) {
        Objects.requireNonNull(type);
        return listeners.getOrDefault(type, EMPTY_DISPATCHER).hasListener();
    }

    @Override
    public int countListeners(Class<?> type) {
        return listeners.getOrDefault(type, EMPTY_DISPATCHER).countListeners();
    }

    private static final class DispatcherProxy<T> implements InvocationHandler {

        private T proxy;
        private final Set<T> listeners = new HashSet<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Object l : listeners) {
                method.invoke(l, args);
            }
            return null;
        }

        public T proxy() {
            return proxy;
        }

        public void addListener(T listener) {
            listeners.add(listener);
        }

        public void removeListener(T listener) {
            listeners.add(listener);
        }

        public boolean hasListener() {
            return !listeners.isEmpty();
        }

        public int countListeners() {
            return listeners.size();
        }

        @SuppressWarnings("unchecked")
        static <T> DispatcherProxy<T> newDispatcher(Class<T> type) {
            DispatcherProxy<T> dispatcherProxy = (DispatcherProxy<T>) new DispatcherProxy();
            dispatcherProxy.proxy = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{type}, dispatcherProxy);
            return dispatcherProxy;
        }
    }
}
