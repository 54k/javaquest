package org.mozilla.browserquest.model.actor.knownlist;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collection;
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
    public boolean addObject(BQObject object) {
        if (object == null) {
            return false;
        }

        if (knowsObject(object)) {
            return false;
        }

        if (!inRange(object)) {
            return false;
        }

        addToKnownList(object);
        return true;
    }

    private boolean inRange(BQObject object) {
        return PositionUtil.isInRange(object, activeObject, getDistanceToFindObject(object));
    }

    public void addToKnownList(BQObject object) {
        knownObjects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            knownPlayers.put(object.getId(), (BQPlayer) object);
        }

        onObjectAddedToKnownList(object);
    }

    protected void onObjectAddedToKnownList(BQObject object) {
    }

    @Override
    public boolean removeObject(BQObject object) {
        if (object == null) {
            return false;
        }

        if (!knowsObject(object)) {
            return false;
        }

        if (!outOfRange(object)) {
            return false;
        }

        removeFromKnownList(object);
        return true;
    }

    private boolean outOfRange(BQObject object) {
        return PositionUtil.isOutOfRange(object, activeObject, getDistanceToForgetObject(object));
    }

    public void removeFromKnownList(BQObject object) {
        knownObjects.remove(object.getId());
        if (object instanceof BQPlayer) {
            knownPlayers.remove(object.getId());
        }
        onObjectRemovedFromKnownList(object);
    }

    protected void onObjectRemovedFromKnownList(BQObject object) {
    }

    @Override
    public void clearKnownList() {
        knownObjects.clear();
        BQWorldRegion region = activeObject.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != activeObject).forEach(object -> object.getKnownList().removeFromKnownList(activeObject));
        }
    }

    @Override
    public boolean knowsObject(BQObject object) {
        return activeObject == object || knownObjects.containsKey(object.getId());
    }

    @Override
    public void updateKnownList() {
        forgetObjects();
        findObjects();
    }

    private void forgetObjects() {
        BQWorldRegion region = activeObject.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != activeObject).forEach(object -> {
                removeObject(object);
                object.getKnownList().removeObject(activeObject);
            });
        }
    }

    protected int getDistanceToForgetObject(BQObject object) {
        return 0;
    }

    private void findObjects() {
        BQWorldRegion region = activeObject.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != activeObject).forEach(object -> {
                addObject(object);
                object.getKnownList().addObject(activeObject);
            });
        }
    }

    protected int getDistanceToFindObject(BQObject object) {
        return 0;
    }
}
