package org.mozilla.browserquest.world;

import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.model.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldInstance {

    private World parent;

    private String name;
    private int maxPlayers;
    private int playersCount;

    private WorldMap worldMap;

    private Set<Player> players = new HashSet<>();

    private Map<String, WorldRegion> worldRegions = new HashMap<>();

    public WorldInstance(World parent, String name, int maxPlayers) {
        this.parent = parent;
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

    public boolean addPlayer(Player player) {
        if (players.add(player)) {
            parent.addPlayer(player);
            player.setWorldInstance(this);
            playersCount++;
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        if (players.remove(player)) {
            parent.removePlayer(player);
            player.setWorldInstance(null);
            player.setWorldRegion(null);
            playersCount--;
            return true;
        }
        return false;
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
        initRegions();
    }

    private void initRegions() {
        worldMap.forEachGroup((id, positions) -> worldRegions.put(id, new WorldRegion(id, this)));
    }

    private WorldRegion getRegion(int x, int y) {
        String regionId = worldMap.getGroupIdFromPosition(x, y);
        WorldRegion worldRegion = worldRegions.get(regionId);
        if (worldRegion == null) {
            worldRegion = new WorldRegion(regionId, this);
            worldRegions.put(regionId, worldRegion);
        }
        return worldRegion;
    }

    public void updatePlayerRegionAndKnownList(Player player) {
        WorldRegion oldWorldRegion = player.getWorldRegion();
        WorldRegion newWorldRegion = getRegion(player.getX(), player.getY());
        if (oldWorldRegion != newWorldRegion) {
            if (oldWorldRegion != null) {
                oldWorldRegion.removeEntity(player);
            }
            newWorldRegion.addEntity(player);

            player.setWorldRegion(newWorldRegion);
        }

        player.getKnownList().update();
    }
}
