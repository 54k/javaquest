package org.mozilla.browserquest.model.collection;

import org.mozilla.browserquest.model.BQObject;

public interface BQObjectContainer<T extends BQObject> extends Iterable<T> {

    int size();

    boolean isEmpty();

    void clear();

    void add(T obj);

    void remove(T obj);

    T get(int id);

    boolean contains(T obj);
}
