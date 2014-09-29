package org.mozilla.browserquest.gameserver.model.position;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface PositionEventListener {

    void onPositionChange();

    void onSpawn();

    void onDecay();
}
