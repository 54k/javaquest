package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.net.NetworkClient;

public class AppSpaceClient<T extends Actor> implements IAppSpaceClient<T> {

    private final NetworkClient networkClient;

    private volatile IAppSpace appSpace;
    private volatile T pawn;

    public AppSpaceClient(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    @Override
    public void setPawn(T pawn) {
        this.pawn = pawn;
    }

    @Override
    public T getPawn() {
        return pawn;
    }

    @Override
    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    @Override
    public void register(IAppSpace appSpace) {
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

    private AppSpaceEventListener getAppSpaceEventListener() {
        return appSpace.getRootObject().post(AppSpaceEventListener.class);
    }
}
