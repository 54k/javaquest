package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

import java.util.Map;

public interface KnownListController {

    Map<Integer, BaseObject> getKnownObjects();

    Map<Integer, PlayerObject> getKnownPlayers();

    boolean addObject(BaseObject object);

    boolean removeObject(BaseObject object);

    void addToKnownList(BaseObject object);

    void removeFromKnownList(BaseObject object);

    void clearKnownList();

    boolean knowsObject(BaseObject object);

    void updateKnownList();
}
