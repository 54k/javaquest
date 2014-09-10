package org.mozilla.browserquest;

import org.mozilla.browserquest.model.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldInstance {

    private String name;
    private int maxPlayers;
    private int playersCount;

    private WorldMap worldMap;

    private Set<Player> players = new HashSet<>();

    private Map<String, GroupContainer> groups = new HashMap<>();

    public WorldInstance(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public boolean isValidPosition(Position position) {
        return isValidPosition(position.getX(), position.getY());
    }

    public boolean isValidPosition(int x, int y) {
        return !worldMap.isOutOfBounds(x, y) && !worldMap.isColliding(x, y);
    }

    public Position getRandomStartingPosition() {
        return worldMap.getRandomStartingPosition();
    }

    public void addPlayer(Player player) {
        if (players.add(player)) {
            player.setWorldInstance(this);
            playersCount++;
        }
    }

    public void removePlayer(Player player) {
        if (players.remove(player)) {
            player.setWorldInstance(null);
            playersCount--;
        }
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void run(WorldMap map) {
        worldMap = map;
        initZoneGroups();
    }

    private void initZoneGroups() {
        worldMap.forEachGroup((id, positions) -> {
            groups.put(id, new GroupContainer());
        });
    }

    private static class GroupContainer {
        private Set<Player> players = new HashSet<>();
    }
}
