package org.mozilla.browserquest.space;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.mozilla.browserquest.actor.Actor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class AppSpace<T extends Actor> {

    // game logic
    private T rootObject;
    private ListeningScheduledExecutorService executor = newExecutor();
    // game controllers
    private Set<SpaceClient> spaceClients = new HashSet<>();

    public AppSpace(T rootObject) {
        this.rootObject = rootObject;
    }

    private ListeningScheduledExecutorService newExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, (ThreadFactory) Thread::new);
        return MoreExecutors.listeningDecorator(scheduledThreadPoolExecutor);
    }

    public ListeningScheduledExecutorService getExecutor() {
        return executor;
    }

    public T getRootObject() {
        return rootObject;
    }

    public Set<SpaceClient> getSpaceClients() {
        return Collections.unmodifiableSet(spaceClients);
    }

    public void attachSpaceClient(SpaceClient spaceClient) {
        spaceClients.add(spaceClient);
    }

    public void detachSpaceClient(SpaceClient spaceClient) {
        spaceClients.remove(spaceClient);
    }
}
