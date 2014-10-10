package org.mozilla.browserquest.space;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface IAppSpaceEventListener {

    void onAppSpaceCreated(IAppSpace appSpace);

    void onAppSpaceDestroyed(IAppSpace appSpace);

    void onAppSpaceTick(long tickDeltaTime);

    void onAppSpaceClientRegistered(IAppSpaceClient appSpaceClient);

    void onAppSpaceClientUnregistered(IAppSpaceClient appSpaceClient);
}