package org.mozilla.browserquest.model.event;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface PositionListener {

    void onSpawn();

    void onDecay();
}
