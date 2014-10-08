package org.mozilla.browserquest.space;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.mozilla.browserquest.actor.Actor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppSpace<T extends Actor> {

    private static final int FPS = 10;

    private T rootObject;
    private ListeningScheduledExecutorService executor;

    public AppSpace(T rootObject) {
        Preconditions.checkNotNull(rootObject);
        this.rootObject = rootObject;
        executor = newExecutor();
        scheduleUpdateTask();
    }

    private ListeningScheduledExecutorService newExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, (r) -> new Thread(r, AppSpace.this.toString()));
        return MoreExecutors.listeningDecorator(scheduledThreadPoolExecutor);
    }

    private void scheduleUpdateTask() {
        executor.execute(this::notifyAppSpaceCreated);
        executor.scheduleAtFixedRate(this::notifyAppSpaceTicked, 0, 1000 / FPS, TimeUnit.MILLISECONDS);
    }

    private void notifyAppSpaceCreated() {
        rootObject.post(AppSpaceEventListener.class).onAppSpaceCreated(this);
    }

    private void notifyAppSpaceDestroyed() {
        rootObject.post(AppSpaceEventListener.class).onAppSpaceDestroyed(this);
    }

    private void notifyAppSpaceTicked() {
        rootObject.post(AppSpaceEventListener.class).onAppSpaceTicked();
    }

    public void destroy() {
        executor.submit(this::notifyAppSpaceDestroyed);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(rootObject=" + rootObject + ')';
    }
}
