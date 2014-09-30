package org.mozilla.browserquest.gameserver.model;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorldMapInstance {

    private static final int REGION_WIDTH = 28;
    private static final int REGION_HEIGHT = 12;

    private int id;
    private WorldMap worldMap;
    private int maxPlayers;

    private int regionOffset;
    private Map<Integer, WorldMapRegion> regions = new ConcurrentHashMap<>();

    private Map<Integer, BaseObject> objects = new ConcurrentHashMap<>();
    private Map<Integer, PlayerObject> players = new ConcurrentHashMap<>();

    public WorldMapInstance(int id, WorldMap worldMap, int maxPlayers) {
        this.id = id;
        this.worldMap = worldMap;
        this.maxPlayers = maxPlayers;
        regionOffset = worldMap.getHeight() / REGION_HEIGHT + 1;
        initRegions();
    }

    private void initRegions() {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();

        for (int i = 0; i < width; i += REGION_WIDTH) {
            for (int j = 0; j < height; j += REGION_HEIGHT) {
                int regionId = getRegionId(i, j);
                regions.put(regionId, new WorldMapRegion());
            }
        }

        for (int i = 0; i < width; i += REGION_WIDTH) {
            for (int j = 0; j < height; j += REGION_HEIGHT) {
                addSurroundingRegions(i, j);
            }
        }
    }

    private int getRegionId(int x, int y) {
        return (x + 1) / REGION_WIDTH * regionOffset + (y + 1) / REGION_HEIGHT;
    }

    private void addSurroundingRegions(int x, int y) {
        WorldMapRegion region = regions.get(getRegionId(x, y));
        for (int i = x - REGION_WIDTH; i <= x + REGION_WIDTH; i += REGION_WIDTH) {
            for (int j = y - REGION_HEIGHT; j <= y + REGION_HEIGHT; j += REGION_HEIGHT) {
                if (isValidRegionPosition(i, j)) {
                    WorldMapRegion sr = regions.get(getRegionId(i, j));
                    region.addSurroundingRegion(sr);
                }
            }
        }
    }

    private boolean isValidRegionPosition(int x, int y) {
        return !worldMap.isOutOfBounds(x, y);
    }

    public int getId() {
        return id;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public WorldMapRegion findRegion(Position position) {
        return findRegion(position.getX(), position.getY());
    }

    public WorldMapRegion findRegion(int x, int y) {
        return regions.get(getRegionId(x, y));
    }

    public Map<Integer, WorldMapRegion> getRegions() {
        return Collections.unmodifiableMap(regions);
    }

    public void addObject(BaseObject object) {
        if (object instanceof PlayerObject) {
            addPlayer((PlayerObject) object);
        }
        objects.put(object.getId(), object);
    }

    public void removeObject(BaseObject object) {
        if (object instanceof PlayerObject) {
            removePlayer((PlayerObject) object);
        }
        objects.remove(object.getId(), object);
    }

    public BaseObject findObject(int id) {
        return objects.get(id);
    }

    public Map<Integer, BaseObject> getObjects() {
        return Collections.unmodifiableMap(players);
    }

    private void addPlayer(PlayerObject player) {
        Preconditions.checkState(players.size() >= maxPlayers);
        players.put(player.getId(), player);
    }

    private void removePlayer(PlayerObject player) {
        players.remove(player.getId());
    }

    public Map<Integer, PlayerObject> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
