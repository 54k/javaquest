package org.mozilla.browserquest.world;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.BQPlayer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldRegion {

    private String id;
    private WorldInstance parent;

    private Map<Integer, BQObject> entities = new HashMap<>();
    private Set<BQPlayer> BQPlayers = new HashSet<>();

    public WorldRegion(String id, WorldInstance parent) {
        this.id = id;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public WorldInstance getParent() {
        return parent;
    }

    public void addEntity(BQObject BQEntity) {
        entities.put(BQEntity.getId(), BQEntity);
        if (BQEntity instanceof BQPlayer) {
            BQPlayers.add((BQPlayer) BQEntity);
        }
    }

    public void removeEntity(BQObject BQEntity) {
        entities.remove(BQEntity.getId());
        if (BQEntity instanceof BQPlayer) {
            BQPlayers.remove(BQEntity);
        }
    }

    public Set<BQPlayer> getPlayers() {
        return Collections.unmodifiableSet(BQPlayers);
    }

    public Map<Integer, BQObject> getEntities() {
        return Collections.unmodifiableMap(entities);
    }
}
