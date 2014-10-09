package org.mozilla.browserquest.space;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import org.mozilla.browserquest.actor.Actor;

public interface IAppSpace<T extends Actor> {

    T getRootObject();

    ListeningScheduledExecutorService getExecutor();

    boolean isInAppSpaceThread();

    void destroy();
}
