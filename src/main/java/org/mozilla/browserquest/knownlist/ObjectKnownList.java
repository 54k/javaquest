package org.mozilla.browserquest.knownlist;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.BQPlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectKnownList extends AbstractKnownList {

    public ObjectKnownList(BQObject activeObject) {
        super(activeObject);
    }

    @Override
    protected Map<Integer, BQObject> newKnownObjectsMap() {
        return new ConcurrentHashMap<>();
    }

    @Override
    protected Map<Integer, BQPlayer> newKnownPlayersMap() {
        return new ConcurrentHashMap<>();
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
