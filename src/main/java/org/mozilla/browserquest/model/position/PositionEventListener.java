package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface PositionEventListener {

    void onPositionChange();

    void onSpawn();

    void onDecay();
}
