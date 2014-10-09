package org.mozilla.browserquest.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingThreadFactory implements ThreadFactory {

    private final String name;
    private final boolean daemon;

    private final AtomicInteger sequence;

    public NamingThreadFactory(String name) {
        this(name, true);
    }

    public NamingThreadFactory(String name, boolean daemon) {
        this.name = name;
        this.daemon = daemon;
        sequence = new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name + "-" + sequence.getAndIncrement());
        thread.setDaemon(daemon);
        return thread;
    }
}
