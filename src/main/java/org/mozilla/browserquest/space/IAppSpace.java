package org.mozilla.browserquest.space;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import org.mozilla.browserquest.util.TypedEventBus;

public interface IAppSpace<T extends TypedEventBus> {

    T getRootObject();

    ListeningScheduledExecutorService getExecutor();

    boolean isInAppSpaceThread();

    void destroy();

    void register(IAppSpaceClient appSpaceClient);

    void unregister(IAppSpaceClient appSpaceClient);
}
