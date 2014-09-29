package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.gameserver.model.actor.BQObject;

public interface KnownListRange {

    int getDistanceToForgetObject(BQObject object);

    int getDistanceToFindObject(BQObject object);
}
