package org.mozilla.browserquest.space;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface AppSpaceEventListener {

    void onAppSpaceCreated(AppSpace appSpace);

    void onAppSpaceTicked();
}
