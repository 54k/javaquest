package org.mozilla.browserquest.space;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.mozilla.browserquest.actor.Actor;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AppSpace<T extends Actor> implements IAppSpace<T> {

    private static final AtomicInteger sequence = new AtomicInteger();
    private static final int DEFAULT_FPS = 10;

    private final T rootObject;
    private final Set<IAppSpaceClient> appSpaceClients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final int fps;
    private final ListeningScheduledExecutorService executor;
    private volatile ListenableScheduledFuture<?> tickTask;
    private volatile Thread eventThread;

    private volatile long prevTickTime;

    public AppSpace(T rootObject) {
        this(rootObject, DEFAULT_FPS);
    }

    public AppSpace(T rootObject, int fps) {
        Preconditions.checkNotNull(rootObject);
        this.rootObject = rootObject;
        this.fps = fps;
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
        int tickPeriod = 1000 / fps;
        tickTask = executor.scheduleAtFixedRate(this::notifyAppSpaceTicked, tickPeriod, tickPeriod, TimeUnit.MILLISECONDS);
    }

    private void notifyAppSpaceCreated() {
        getAppSpaceEventListener().onAppSpaceCreated(this);
    }

    private void notifyAppSpaceTicked() {
        long currentTickTime = System.currentTimeMillis();
        getAppSpaceEventListener().onAppSpaceTick(currentTickTime - prevTickTime);
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

    private void notifyAppSpaceDestroyed() {
        getAppSpaceEventListener().onAppSpaceDestroyed(this);
    }

    @Override
    public void register(IAppSpaceClient appSpaceClient) {
        if (appSpaceClients.add(appSpaceClient)) {
            invokeLater(() -> appSpaceClient.register(this));
        }
    }

    @Override
    public void unregister(IAppSpaceClient appSpaceClient) {
        if (appSpaceClients.remove(appSpaceClient)) {
            invokeLater(appSpaceClient::unregister);
        }
    }

    @Override
    public void destroy() {
        tickTask.cancel(false);
        invokeLater(this::unregisterAppSpaceClients);
        executor.submit(this::notifyAppSpaceDestroyed).addListener(executor::shutdown, executor);
    }

    private void unregisterAppSpaceClients() {
        appSpaceClients.forEach(IAppSpaceClient::unregister);
    }

    private IAppSpaceEventListener getAppSpaceEventListener() {
        return rootObject.post(IAppSpaceEventListener.class);
    }

    private void invokeLater(Runnable runnable) {
        executor.execute(runnable);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(rootObject=" + rootObject + ')';
    }
}
