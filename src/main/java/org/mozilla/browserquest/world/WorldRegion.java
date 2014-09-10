package org.mozilla.browserquest.world;

import org.mozilla.browserquest.model.Entity;
import org.mozilla.browserquest.model.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldRegion {

    private String id;
    private WorldInstance parent;

    private Map<Integer, Entity> entities = new HashMap<>();
    private Set<Player> players = new HashSet<>();

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

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
        if (entity instanceof Player) {
            players.add((Player) entity);
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getId());
        if (entity instanceof Player) {
            players.remove(entity);
        }
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public Map<Integer, Entity> getEntities() {
        return Collections.unmodifiableMap(entities);
    }
}
