package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractKnownList implements KnownList {

    private final BQObject activeObject;
    private final Map<Integer, BQObject> knownObjects;
    private final Map<Integer, BQPlayer> knownPlayers;

    protected AbstractKnownList(BQObject activeObject) {
        this.activeObject = activeObject;
        knownObjects = new ConcurrentHashMap<>();
        knownPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public BQObject getActiveObject() {
        return activeObject;
    }

    @Override
    public Map<Integer, BQObject> getKnownObjects() {
        return knownObjects;
    }

    @Override
    public Map<Integer, BQPlayer> getKnownPlayers() {
        return knownPlayers;
    }

    @Override
    public boolean addKnownObject(BQObject object) {
        if (knowsObject(object)) {
            return false;
        }

        knownObjects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            knownPlayers.put(object.getId(), (BQPlayer) object);
        }

        activeObject.onObjectAddedToKnownList(object);
        object.getKnownList().addKnownObject(activeObject);
        return true;
    }

    @Override
    public boolean removeKnownObject(BQObject object) {
        if (!knowsObject(object)) {
            return false;
        }

        knownObjects.remove(object.getId());
        if (object instanceof BQPlayer) {
            knownPlayers.remove(object.getId());
        }
        activeObject.onObjectRemovedFromKnownList(object);
        object.getKnownList().removeKnownObject(activeObject);
        return true;
    }

    @Override
    public void clearKnownObjects() {
        Iterator<BQObject> it = knownObjects.values().iterator();
        while (it.hasNext()) {
            BQObject object = it.next();
            it.remove();
            object.getKnownList().removeKnownObject(activeObject);
        }
    }

    @Override
    public boolean knowsObject(BQObject object) {
        return activeObject == object || knownObjects.containsKey(object.getId());
    }

    @Override
    public void updateKnownObjects() {
        clearInvisibleObjects();
        findVisibleObjects();
    }

    private void clearInvisibleObjects() {
        Iterator<BQObject> it = knownObjects.values().iterator();
        while (it.hasNext()) {
            BQObject object = it.next();
            if (!isObjectInRange(object, getDistanceToForgetObject(object))) {
                it.remove();
                activeObject.onObjectRemovedFromKnownList(object);
                object.getKnownList().removeKnownObject(activeObject);
            }
        }
    }

    protected abstract int getDistanceToForgetObject(BQObject object);

    private void findVisibleObjects() {
        Collection<BQObject> objects = activeObject.getRegion().getEntities().values();
        objects.stream().filter(object -> object != activeObject && isObjectInRange(object, getDistanceToFindObject(object))).forEach(this::addKnownObject);
    }

    protected abstract int getDistanceToFindObject(BQObject object);

    private boolean isObjectInRange(BQObject object, int range) {
        return Math.abs(object.getX() - activeObject.getX()) <= range && Math.abs(object.getY() - activeObject.getY()) <= range;
    }
}
