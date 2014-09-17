package org.mozilla.browserquest.model.collection;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.exception.DuplicateObjectException;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBQObjectContainer<T extends BQObject> implements BQObjectContainer<T> {

    private final Map<Integer, T> delegate;

    public DefaultBQObjectContainer() {
        delegate = new ConcurrentHashMap<>();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public void add(T obj) {
        if (delegate.putIfAbsent(obj.getId(), obj) != null) {
            throw new DuplicateObjectException();
        }
    }

    @Override
    public void remove(T obj) {
        delegate.remove(obj.getId());
    }

    @Override
    public T get(int id) {
        return delegate.get(id);
    }

    @Override
    public boolean contains(T obj) {
        return delegate.containsKey(obj.getId());
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.values().iterator();
    }
}
