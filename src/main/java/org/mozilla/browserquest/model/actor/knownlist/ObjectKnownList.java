package org.mozilla.browserquest.model.actor.knownlist;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectKnownList implements KnownList {

    private BQObject activeObject;
    private Map<Integer, BQObject> knownObjects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> knownPlayers = new ConcurrentHashMap<>();

    public ObjectKnownList(BQObject activeObject) {
        Preconditions.checkNotNull(activeObject);
        this.activeObject = activeObject;
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
        if (object == null) {
            return false;
        }

        if (knowsObject(object)) {
            return false;
        }

        knownObjects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            knownPlayers.put(object.getId(), (BQPlayer) object);
        }

        onObjectAddedToKnownList(object);
        return true;
    }

    protected void onObjectAddedToKnownList(BQObject object) {
    }

    @Override
    public boolean removeKnownObject(BQObject object) {
        if (object == null) {
            return false;
        }

        if (!knowsObject(object)) {
            return false;
        }

        knownObjects.remove(object.getId());
        if (object instanceof BQPlayer) {
            knownPlayers.remove(object.getId());
        }
        onObjectRemovedFromKnownList(object);
        return true;
    }

    protected void onObjectRemovedFromKnownList(BQObject object) {
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
        forgetObjects();
        findObjects();
    }

    private void forgetObjects() {
        Iterator<BQObject> it = knownObjects.values().iterator();
        while (it.hasNext()) {
            BQObject object = it.next();
            if (!isObjectInRange(object, getDistanceToForgetObject(object))) {
                it.remove();
                onObjectRemovedFromKnownList(object);
                object.getKnownList().removeKnownObject(activeObject);
            }
        }
    }

    protected int getDistanceToForgetObject(BQObject object) {
        return 0;
    }

    private void findObjects() {
        BQWorldRegion region = activeObject.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != activeObject && isObjectInRange(object, getDistanceToFindObject(object))).forEach(this::addKnownObject);
        }
    }

    protected int getDistanceToFindObject(BQObject object) {
        return 0;
    }

    private boolean isObjectInRange(BQObject object, int range) {
        return Math.abs(object.getX() - activeObject.getX()) <= range && Math.abs(object.getY() - activeObject.getY()) <= range;
    }
}
