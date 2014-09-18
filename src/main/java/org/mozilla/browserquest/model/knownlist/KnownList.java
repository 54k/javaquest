package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Map;

public interface KnownList<T extends BQObject> {

    T getActiveObject();

    Map<Integer, BQObject> getKnownObjects();

    Map<Integer, BQPlayer> getKnownPlayers();

    boolean addKnownObject(BQObject object);

    boolean removeKnownObject(BQObject object);

    void clearKnownObjects();

    boolean knowsObject(BQObject object);

    void updateKnownObjects();
}
