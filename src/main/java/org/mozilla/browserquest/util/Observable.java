package org.mozilla.browserquest.util;

public interface Observable {

    <T> T notify(Class<T> type);

    <T> void addListener(Class<T> type, T listener);

    <T> void removeListener(Class<T> type, T listener);

    void dropListener(Object listener);

    void dropListeners();

    boolean hasListeners(Class<?> type);

    int countListeners(Class<?> type);
}
