package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Actor;

public interface IAppSpaceClient<T extends Actor> {

    void setPawn(T pawn);

    T getPawn();

    IAppSpace<? extends Actor> getAppSpace();

    void register(IAppSpace<? extends Actor> appSpace);

    void unregister();
}
