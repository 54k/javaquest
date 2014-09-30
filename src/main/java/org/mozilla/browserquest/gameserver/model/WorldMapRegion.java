package org.mozilla.browserquest.gameserver.model;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldMapRegion {

    private Map<Integer, BaseObject> objects = new ConcurrentHashMap<>();
    private Map<Integer, PlayerObject> players = new ConcurrentHashMap<>();

    private Set<WorldMapRegion> surroundingRegions = new HashSet<>();

    private boolean active;

    public void addSurroundingRegion(WorldMapRegion region) {
        Preconditions.checkNotNull(region);
        surroundingRegions.add(region);
    }

    public Set<WorldMapRegion> getSurroundingRegions() {
        return Collections.unmodifiableSet(surroundingRegions);
    }

    public void addObject(BaseObject object) {
        objects.put(object.getId(), object);
        if (object instanceof PlayerObject) {
            players.put(object.getId(), (PlayerObject) object);
            if (players.size() == 1) {
                activateRegion();
            }
        }
    }

    private void activateRegion() {
        active = true;
    }

    public void removeObject(BaseObject object) {
        objects.remove(object.getId());
        if (object instanceof PlayerObject) {
            players.remove(object.getId());
            if (players.isEmpty()) {
                deactivateRegion();
            }
        }
    }

    private void deactivateRegion() {
        active = false;
    }

    public Map<Integer, BaseObject> getObjects() {
        return Collections.unmodifiableMap(objects);
    }

    public Map<Integer, PlayerObject> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    public boolean isActive() {
        return active;
    }
}
