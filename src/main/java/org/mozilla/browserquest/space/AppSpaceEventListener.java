package org.mozilla.browserquest.space;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface AppSpaceEventListener {

    void onAppSpaceCreated(IAppSpace appSpace);

    void onAppSpaceDestroyed(IAppSpace appSpace);

    void onAppSpaceTick(long tickDeltaTime);
}
