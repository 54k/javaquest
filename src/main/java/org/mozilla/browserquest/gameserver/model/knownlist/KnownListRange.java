package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;

public interface KnownListRange {

    int getDistanceToForgetObject(BaseObject object);

    int getDistanceToFindObject(BaseObject object);
}
