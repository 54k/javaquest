package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Actor;

public class AppSpaceClient<T extends Actor> implements IAppSpaceClient<T> {

    private IAppSpace<? extends Actor> appSpace;
    private T pawn;

    @Override
    public void setPawn(T pawn) {
        this.pawn = pawn;
    }

    @Override
    public T getPawn() {
        return pawn;
    }

    @Override
    public IAppSpace<? extends Actor> getAppSpace() {
        return appSpace;
    }

    @Override
    public void register(IAppSpace<? extends Actor> appSpace) {
        this.appSpace = appSpace;
        notifyAppSpaceClientRegistered();
    }

    private void notifyAppSpaceClientRegistered() {
        getAppSpaceEventListener().onAppSpaceClientRegistered(this);
    }

    @Override
    public void unregister() {
        notifyAppSpaceClientUnregistered();
        this.appSpace = null;
    }

    private void notifyAppSpaceClientUnregistered() {
        getAppSpaceEventListener().onAppSpaceClientUnregistered(this);
    }

    private IAppSpaceEventListener getAppSpaceEventListener() {
        return appSpace.getRootObject().post(IAppSpaceEventListener.class);
    }
}
