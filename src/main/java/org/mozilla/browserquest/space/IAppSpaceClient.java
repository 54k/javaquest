package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.net.NetworkClient;

public interface IAppSpaceClient<T extends Actor> {

    void setPawn(T pawn);

    T getPawn();

    NetworkClient getNetworkClient();

    void register(IAppSpace appSpace);

    void unregister();
}
