package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collection;
import java.util.Map;

@BehaviorPrototype(KnownListController.class)
public class KnownListControllerBehavior extends Behavior<BQObject> implements KnownListController {

    @Override
    public Map<Integer, BQObject> getKnownObjects() {
        return getActor().getKnownObjects();
    }

    @Override
    public Map<Integer, BQPlayer> getKnownPlayers() {
        return getActor().getKnownPlayers();
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
        BQObject actor = getActor();
        return PositionUtil.isInRange(object, actor, actor.getDistanceToFindObject(object));
    }

    @Override
    public void addToKnownList(BQObject object) {
        BQObject actor = getActor();
        actor.getKnownObjects().put(object.getId(), object);
        if (object instanceof BQPlayer) {
            actor.getKnownPlayers().put(object.getId(), (BQPlayer) object);
        }

        actor.onObjectAddedToKnownList(object);
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
        return PositionUtil.isOutOfRange(object, getActor(), getActor().getDistanceToForgetObject(object));
    }

    public void removeFromKnownList(BQObject object) {
        BQObject actor = getActor();
        actor.getKnownObjects().remove(object.getId());
        if (object instanceof BQPlayer) {
            actor.getKnownPlayers().remove(object.getId());
        }
        actor.onObjectRemovedFromKnownList(object);
    }

    @Override
    public void clearKnownList() {
        BQObject actor = getActor();
        actor.getKnownObjects().clear();
        actor.getKnownPlayers().clear();

        BQWorldRegion region = actor.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> object.getKnownListController().removeFromKnownList(actor));
        }
    }

    @Override
    public boolean knowsObject(BQObject object) {
        BQObject actor = getActor();
        return actor == object || actor.getKnownObjects().containsKey(object.getId());
    }

    @Override
    public void updateKnownList() {
        forgetObjects();
        findObjects();
    }

    private void forgetObjects() {
        BQObject actor = getActor();
        BQWorldRegion region = actor.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                removeObject(object);
                object.getKnownListController().removeObject(actor);
            });
        }
    }

    private void findObjects() {
        BQObject actor = getActor();
        BQWorldRegion region = actor.getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                addObject(object);
                object.getKnownListController().addObject(actor);
            });
        }
    }
}
