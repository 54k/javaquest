package org.mozilla.browserquest.model;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldRegion {

    private Map<Integer, BQObject> objects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> players = new ConcurrentHashMap<>();

    private Set<BQWorldRegion> surroundingRegions = new HashSet<>();

    void addSurroundingRegion(BQWorldRegion region) {
        Preconditions.checkNotNull(region);
        surroundingRegions.add(region);
    }

    public Set<BQWorldRegion> getSurroundingRegions() {
        return Collections.unmodifiableSet(surroundingRegions);
    }

    public void addObject(BQObject object) {
        objects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            players.put(object.getId(), (BQPlayer) object);
        }
    }

    public void removeObject(BQObject object) {
        objects.remove(object.getId());
        if (object instanceof BQPlayer) {
            players.remove(object.getId());
        }
    }

    public Map<Integer, BQObject> getObjects() {
        return Collections.unmodifiableMap(objects);
    }

    public Map<Integer, BQPlayer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
