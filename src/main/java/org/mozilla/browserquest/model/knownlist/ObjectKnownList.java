package org.mozilla.browserquest.model.knownlist;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.behavior.Positionable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectKnownList<T extends BQObject> implements KnownList<T> {

    private T activeObject;
    private Map<Integer, BQObject> knownObjects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> knownPlayers = new ConcurrentHashMap<>();

    public ObjectKnownList(T activeObject) {
        Preconditions.checkNotNull(activeObject);
        this.activeObject = activeObject;
    }

    @Override
    public T getActiveObject() {
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

        knownObjects.put(object.getObjectId(), object);
        if (object instanceof BQPlayer) {
            knownPlayers.put(object.getObjectId(), (BQPlayer) object);
        }

        onObjectAddedToKnownList(object);
        object.asBehavior(Positionable.class).getKnownList().addKnownObject(activeObject);
        return true;
    }

    protected void onObjectAddedToKnownList(BQObject object) {
    }

    @Override
    public boolean removeKnownObject(BQObject object) {
        if (!knowsObject(object)) {
            return false;
        }

        knownObjects.remove(object.getObjectId());
        if (object instanceof BQPlayer) {
            knownPlayers.remove(object.getObjectId());
        }
        onObjectRemovedFromKnownList(object);
        object.asBehavior(Positionable.class).getKnownList().removeKnownObject(activeObject);
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
            object.asBehavior(Positionable.class).getKnownList().removeKnownObject(activeObject);
        }
    }

    @Override
    public boolean knowsObject(BQObject object) {
        return activeObject == object || knownObjects.containsKey(object.getObjectId());
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
                object.asBehavior(Positionable.class).getKnownList().removeKnownObject(activeObject);
            }
        }
    }

    protected int getDistanceToForgetObject(BQObject object) {
        return 0;
    }

    private void findObjects() {
        Collection<BQObject> objects = activeObject.asBehavior(Positionable.class).getRegion().getObjects().values();
        objects.stream().filter(object -> object != activeObject && isObjectInRange(object, getDistanceToFindObject(object))).forEach(this::addKnownObject);
    }

    protected int getDistanceToFindObject(BQObject object) {
        return 0;
    }

    private boolean isObjectInRange(BQObject object, int range) {
        Positionable activeObjectPos = activeObject.asBehavior(Positionable.class);
        Positionable objectPos = object.asBehavior(Positionable.class);
        return Math.abs(objectPos.getX() - activeObjectPos.getX()) <= range && Math.abs(objectPos.getY() - activeObjectPos.getY()) <= range;
    }
}
