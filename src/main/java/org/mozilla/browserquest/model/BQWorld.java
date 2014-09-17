package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.collection.BQObjectContainer;
import org.mozilla.browserquest.model.collection.BQPlayerContainer;
import org.mozilla.browserquest.model.collection.DefaultBQObjectContainer;
import org.mozilla.browserquest.model.collection.DefaultBQPlayerContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorld {

    private BQPlayerContainer players = new DefaultBQPlayerContainer();
    private BQObjectContainer<BQObject> objects = new DefaultBQObjectContainer<>();

    private Map<Integer, BQWorldMap> worldMapById = new ConcurrentHashMap<>();

    public void storeObject(BQObject object) {
        objects.add(object);
    }

    public void removeObject(BQObject object) {
        objects.remove(object);
    }

    public BQObject findObjectById(int id) {
        return objects.get(id);
    }

    public void storePlayer(BQPlayer player) {
        players.add(player);
    }

    public void removePlayer(BQPlayer player) {
        players.remove(player.getName());
    }

    public BQPlayer findPlayerById(int id) {
        return players.get(id);
    }

    public BQPlayer findPlayerByName(String name) {
        return players.get(name);
    }

    private void addWorldMap(BQWorldMap worldMap) {
        worldMapById.put(worldMap.getId(), worldMap);
    }

    public BQWorldMap findWorldMapById(int id) {
        return worldMapById.get(id);
    }
}
