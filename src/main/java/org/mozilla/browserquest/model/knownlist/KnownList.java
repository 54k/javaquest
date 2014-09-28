package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Map;

public interface KnownList {

    Map<Integer, BQObject> getKnownObjects();

    Map<Integer, BQPlayer> getKnownPlayers();

    boolean addObject(BQObject object);

    boolean removeObject(BQObject object);

    void addToKnownList(BQObject object);

    void removeFromKnownList(BQObject object);

    void clearKnownList();

    boolean knowsObject(BQObject object);

    void updateKnownList();
}
