package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.WorldRegion;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ComponentPrototype(KnownListController.class)
public class KnownListControllerComponent extends Component<BaseObject> implements KnownListController {

    private Map<Integer, BaseObject> knownObjects = new ConcurrentHashMap<>();
    private Map<Integer, PlayerObject> knownPlayers = new ConcurrentHashMap<>();

    @Override
    public Map<Integer, BaseObject> getKnownObjects() {
        return Collections.unmodifiableMap(knownObjects);
    }

    @Override
    public Map<Integer, PlayerObject> getKnownPlayers() {
        return Collections.unmodifiableMap(knownPlayers);
    }

    @Override
    public boolean addObject(BaseObject object) {
        if (!knowsObject(object) && inRange(object)) {
            addToKnownList(object);
            return true;
        }
        return false;
    }

    private boolean inRange(BaseObject object) {
        BaseObject actor = getActor();
        int distanceToFindObject = actor.getKnownListRange().getDistanceToFindObject(object);
        return PositionUtil.isInRange(object, actor, distanceToFindObject);
    }

    @Override
    public void addToKnownList(BaseObject object) {
        knownObjects.put(object.getId(), object);
        if (object instanceof PlayerObject) {
            knownPlayers.put(object.getId(), (PlayerObject) object);
        }
        getActor().post(KnownListEventListener.class).onObjectAddedToKnownList(object);
    }

    @Override
    public boolean removeObject(BaseObject object) {
        if (knowsObject(object) && outOfRange(object)) {
            removeFromKnownList(object);
            return true;
        }
        return false;
    }

    private boolean outOfRange(BaseObject object) {
        BaseObject actor = getActor();
        int distanceToForgetObject = actor.getKnownListRange().getDistanceToForgetObject(object);
        return PositionUtil.isOutOfRange(object, actor, distanceToForgetObject);
    }

    public void removeFromKnownList(BaseObject object) {
        BaseObject actor = getActor();
        knownObjects.remove(object.getId());
        if (object instanceof PlayerObject) {
            knownPlayers.remove(object.getId());
        }
        actor.post(KnownListEventListener.class).onObjectRemovedFromKnownList(object);
    }

    @Override
    public void clearKnownList() {
        BaseObject actor = getActor();
        knownObjects.clear();
        knownPlayers.clear();

        WorldRegion region = actor.getPositionController().getRegion();
        for (WorldRegion sr : region.getSurroundingRegions()) {
            Collection<BaseObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> object.getKnownListController().removeFromKnownList(actor));
        }
    }

    @Override
    public boolean knowsObject(BaseObject object) {
        BaseObject actor = getActor();
        return actor == object || knownObjects.containsKey(object.getId());
    }

    @Override
    public void updateKnownList() {
        forgetObjects();
        findObjects();
    }

    private void forgetObjects() {
        BaseObject actor = getActor();
        WorldRegion region = actor.getPositionController().getRegion();
        for (WorldRegion sr : region.getSurroundingRegions()) {
            Collection<BaseObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                removeObject(object);
                object.getKnownListController().removeObject(actor);
            });
        }
    }

    private void findObjects() {
        BaseObject actor = getActor();
        WorldRegion region = actor.getPositionController().getRegion();
        for (WorldRegion sr : region.getSurroundingRegions()) {
            Collection<BaseObject> objects = sr.getObjects().values();
            objects.stream().filter(object -> object != actor).forEach(object -> {
                addObject(object);
                object.getKnownListController().addObject(actor);
            });
        }
    }
}
