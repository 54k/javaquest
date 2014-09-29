package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.BQWorldRegion;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.actor.BQPlayer;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ComponentPrototype(KnownList.class)
public class KnownListComponent extends Component<BQObject> implements KnownList {

    private Map<Integer, BQObject> knownObjects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> knownPlayers = new ConcurrentHashMap<>();

    @Override
    public Map<Integer, BQObject> getKnownObjects() {
        return Collections.unmodifiableMap(knownObjects);
    }

    @Override
    public Map<Integer, BQPlayer> getKnownPlayers() {
        return Collections.unmodifiableMap(knownPlayers);
    }

    @Override
    public boolean addObject(BQObject object) {
        if (!knowsObject(object) && inRange(object)) {
            addToKnownList(object);
            return true;
        }
        return false;
    }

    private boolean inRange(BQObject object) {
        BQObject actor = getActor();
        int distanceToFindObject = actor.getKnownListRange().getDistanceToFindObject(object);
        return PositionUtil.isInRange(object, actor, distanceToFindObject);
    }

    @Override
    public void addToKnownList(BQObject object) {
        knownObjects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            knownPlayers.put(object.getId(), (BQPlayer) object);
        }
        getActor().post(KnownListEventListener.class).onObjectAddedToKnownList(object);
    }

    @Override
    public boolean removeObject(BQObject object) {
        if (knowsObject(object) && outOfRange(object)) {
            removeFromKnownList(object);
            return true;
        }
        return false;
    }

    private boolean outOfRange(BQObject object) {
        BQObject actor = getActor();
        int distanceToForgetObject = actor.getKnownListRange().getDistanceToForgetObject(object);
        return PositionUtil.isOutOfRange(object, actor, distanceToForgetObject);
    }

    public void removeFromKnownList(BQObject object) {
        BQObject actor = getActor();
        knownObjects.remove(object.getId());
        if (object instanceof BQPlayer) {
            knownPlayers.remove(object.getId());
        }
        actor.post(KnownListEventListener.class).onObjectRemovedFromKnownList(object);
    }

    @Override
    public void clearKnownList() {
        BQObject actor = getActor();
        knownObjects.clear();
        knownPlayers.clear();

        BQWorldRegion region = actor.getPositionController().getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> object.getKnownList().removeFromKnownList(actor));
        }
    }

    @Override
    public boolean knowsObject(BQObject object) {
        BQObject actor = getActor();
        return actor == object || knownObjects.containsKey(object.getId());
    }

    @Override
    public void updateKnownList() {
        forgetObjects();
        findObjects();
    }

    private void forgetObjects() {
        BQObject actor = getActor();
        BQWorldRegion region = actor.getPositionController().getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                removeObject(object);
                object.getKnownList().removeObject(actor);
            });
        }
    }

    private void findObjects() {
        BQObject actor = getActor();
        BQWorldRegion region = actor.getPositionController().getRegion();
        for (BQWorldRegion sr : region.getSurroundingRegions()) {
            Collection<BQObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                addObject(object);
                object.getKnownList().addObject(actor);
            });
        }
    }
}
