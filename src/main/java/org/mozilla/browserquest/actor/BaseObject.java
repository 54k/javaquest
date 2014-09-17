package org.mozilla.browserquest.actor;

import io.gwynt.util.DefaultObservable;
import io.gwynt.util.DefaultReferenceCounted;
import io.gwynt.util.Observable;
import io.gwynt.util.ReferenceCounted;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseObject implements Observable, ReferenceCounted {

    private final Map<BaseObjectMetadata, Object> metadata = new ConcurrentHashMap<>();

    private final ReferenceCounted refCnt = new DefaultReferenceCounted();
    private final Observable observable = new DefaultObservable();

    BaseObject() {
    }

    @Override
    public int refCnt() {
        return refCnt.refCnt();
    }

    @Override
    public BaseObject retain() {
        refCnt.retain();
        return this;
    }

    @Override
    public BaseObject retain(int increment) {
        refCnt.retain(increment);
        return this;
    }

    @Override
    public boolean release() {
        return refCnt.release();
    }

    @Override
    public boolean release(int decrement) {
        return refCnt.release(decrement);
    }

    @Override
    public <T> T notify(Class<T> type) {
        return observable.notify(type);
    }

    @Override
    public <T> void addListener(Class<T> type, T listener) {
        observable.addListener(type, listener);
    }

    @Override
    public <T> void removeListener(Class<T> type, T listener) {
        observable.removeListener(type, listener);
    }

    @Override
    public void dropListener(Object listener) {
        observable.dropListener(listener);
    }

    @Override
    public void dropListeners() {
        observable.dropListeners();
    }

    @Override
    public boolean hasListeners(Class<?> type) {
        return observable.hasListeners(type);
    }

    @Override
    public int countListeners(Class<?> type) {
        return observable.countListeners(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T setMetadata(BaseObjectMetadata<T> channelOption, T value) {
        return (T) metadata.put(channelOption, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata(BaseObjectMetadata<T> channelOption) {
        return (T) metadata.get(channelOption);
    }
}
