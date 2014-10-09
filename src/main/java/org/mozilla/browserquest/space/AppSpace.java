package org.mozilla.browserquest.space;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.mozilla.browserquest.actor.Actor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AppSpace<T extends Actor> implements IAppSpace<T> {

    private static final AtomicInteger sequence = new AtomicInteger();
    private static final int FPS = 10;

    private final T rootObject;

    private final ListeningScheduledExecutorService executor;
    private volatile ListenableScheduledFuture<?> tickTask;
    private volatile Thread eventThread;

    private volatile long prevTickTime;

    public AppSpace(T rootObject) {
        Preconditions.checkNotNull(rootObject);
        this.rootObject = rootObject;
        prevTickTime = System.currentTimeMillis();
        executor = newExecutor();
        scheduleUpdateTask();
    }

    private ListeningScheduledExecutorService newExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, (r) -> eventThread = new Thread(r, "app-space-" + sequence.getAndIncrement()));
        return MoreExecutors.listeningDecorator(scheduledThreadPoolExecutor);
    }

    private void scheduleUpdateTask() {
        executor.execute(this::notifyAppSpaceCreated);
        int tickPeriod = 1000 / FPS;
        tickTask = executor.scheduleAtFixedRate(this::notifyAppSpaceTicked, tickPeriod, tickPeriod, TimeUnit.MILLISECONDS);
    }

    private void notifyAppSpaceCreated() {
        rootObject.post(AppSpaceEventListener.class).onAppSpaceCreated(this);
    }

    private void notifyAppSpaceTicked() {
        long currentTickTime = System.currentTimeMillis();
        rootObject.post(AppSpaceEventListener.class).onAppSpaceTick(currentTickTime - prevTickTime);
        prevTickTime = currentTickTime;
    }

    @Override
    public T getRootObject() {
        return rootObject;
    }

    @Override
    public ListeningScheduledExecutorService getExecutor() {
        return executor;
    }

    @Override
    public boolean isInAppSpaceThread() {
        return eventThread == Thread.currentThread();
    }

    @Override
    public void destroy() {
        tickTask.cancel(false);
        executor.submit(this::notifyAppSpaceDestroyed).addListener(executor::shutdown, executor);
    }

    private void notifyAppSpaceDestroyed() {
        rootObject.post(AppSpaceEventListener.class).onAppSpaceDestroyed(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(rootObject=" + rootObject + ')';
    }
}
