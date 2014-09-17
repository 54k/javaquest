package org.mozilla.browserquest.model.collection;

import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBQPlayerContainer implements BQPlayerContainer {

    private Map<Integer, BQPlayer> playersById = new ConcurrentHashMap<>();
    private Map<String, BQPlayer> playersByName = new ConcurrentHashMap<>();

    @Override
    public void add(BQPlayer player) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void remove(String name) {
    }

    @Override
    public BQPlayer get(int id) {
        return playersById.get(id);
    }

    @Override
    public BQPlayer get(String name) {
        return playersByName.get(name);
    }

    @Override
    public int size() {
        return playersById.size();
    }

    @Override
    public boolean contains(BQPlayer player) {
        return playersById.containsKey(player.getId());
    }

    @Override
    public boolean contains(int id) {
        return playersById.containsKey(id);
    }

    @Override
    public boolean contains(String name) {
        return playersByName.containsKey(name);
    }

    @Override
    public void clear() {
        playersById.clear();
        playersByName.clear();
    }

    @Override
    public Iterator<BQPlayer> iterator() {
        return playersById.values().iterator();
    }
}
