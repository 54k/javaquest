package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.actor.BQObject;

public interface KnownListRange {

    int getDistanceToForgetObject(BQObject object);

    int getDistanceToFindObject(BQObject object);
}
