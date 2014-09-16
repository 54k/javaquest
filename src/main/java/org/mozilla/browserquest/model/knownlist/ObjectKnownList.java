package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectKnownList extends AbstractKnownList {

    public ObjectKnownList(BQObject activeObject) {
        super(activeObject);
    }

    @Override
    protected int getDistanceToFindObject(BQObject object) {
        return 0;
    }

    @Override
    protected int getDistanceToForgetObject(BQObject object) {
        return 0;
    }
}
