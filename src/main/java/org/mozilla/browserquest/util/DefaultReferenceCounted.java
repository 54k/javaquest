package org.mozilla.browserquest.util;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class DefaultReferenceCounted implements ReferenceCounted {

    private static final AtomicIntegerFieldUpdater<DefaultReferenceCounted> FIELD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(DefaultReferenceCounted.class, "refCnt");
    private volatile transient int refCnt = 0;

    @Override
    public int refCnt() {
        return FIELD_UPDATER.get(this);
    }

    @Override
    public ReferenceCounted retain() {
        FIELD_UPDATER.incrementAndGet(this);
        return this;
    }

    @Override
    public ReferenceCounted retain(int increment) {
        if (increment <= 0) {
            throw new IllegalArgumentException("increment must be positive value");
        }
        FIELD_UPDATER.addAndGet(this, increment);
        return this;
    }

    @Override
    public boolean release() {
        return FIELD_UPDATER.decrementAndGet(this) > 0;
    }

    @Override
    public boolean release(int decrement) {
        if (decrement <= 0) {
            throw new IllegalArgumentException("decrement must be positive value");
        }
        return FIELD_UPDATER.addAndGet(this, -decrement) > 0;
    }
}
