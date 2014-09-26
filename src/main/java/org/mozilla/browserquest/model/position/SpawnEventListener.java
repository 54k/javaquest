package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.util.Listener;

@Listener
public interface SpawnEventListener {

    void onSpawn();

    void onDecay();
}
