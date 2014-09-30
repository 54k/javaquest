package org.mozilla.browserquest.gameserver.model;

import com.google.inject.Inject;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.template.WorldTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private static final int REGION_WIDTH = 28;
    private static final int REGION_HEIGHT = 12;

    private final int width;
    private final int height;
    private final int regionOffset;

    private Map<Integer, WorldRegion> regions = new ConcurrentHashMap<>();

    private Map<Integer, BaseObject> objects = new ConcurrentHashMap<>();
    private Map<Integer, PlayerObject> players = new ConcurrentHashMap<>();

    @Inject
    public World(WorldTemplate worldTemplate) {
        width = worldTemplate.getWidth();
        height = worldTemplate.getHeight();
        regionOffset = height / REGION_HEIGHT + 1;
        initRegions();
    }

    private void initRegions() {
        for (int i = 0; i < width; i += REGION_WIDTH) {
            for (int j = 0; j < height; j += REGION_HEIGHT) {
                int regionId = getRegionId(i, j);
                regions.put(regionId, new WorldRegion());
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
        WorldRegion region = regions.get(getRegionId(x, y));
        for (int i = x - REGION_WIDTH; i <= x + REGION_WIDTH; i += REGION_WIDTH) {
            for (int j = y - REGION_HEIGHT; j <= y + REGION_HEIGHT; j += REGION_HEIGHT) {
                if (isValidRegionPosition(i, j)) {
                    WorldRegion sr = regions.get(getRegionId(i, j));
                    region.addSurroundingRegion(sr);
                }
            }
        }
    }

    private boolean isValidRegionPosition(int x, int y) {
        return x >= 0 && y >= 0 && x <= width && y <= height;
    }

    public WorldRegion findRegion(Position position) {
        return findRegion(position.getX(), position.getY());
    }

    public WorldRegion findRegion(int x, int y) {
        return regions.get(getRegionId(x, y));
    }

    public Map<Integer, WorldRegion> getRegions() {
        return Collections.unmodifiableMap(regions);
    }

    public Position findPositionFromTileIndex(int tileIndex) {
        int x = Math.max((tileIndex % width == 0) ? width - 1 : (tileIndex % width) - 1, 0);
        int y = (int) Math.floor((tileIndex - 1) / width);
        return new Position(x, y);
    }

    public void addObject(BaseObject object) {
        objects.put(object.getId(), object);
        if (object instanceof PlayerObject) {
            addPlayer((PlayerObject) object);
        }
    }

    public void removeObject(BaseObject object) {
        objects.remove(object.getId(), object);
        if (object instanceof PlayerObject) {
            removePlayer((PlayerObject) object);
        }
    }

    public BaseObject findObject(int id) {
        return objects.get(id);
    }

    public Map<Integer, BaseObject> getObjects() {
        return Collections.unmodifiableMap(players);
    }

    private void addPlayer(PlayerObject player) {
        players.put(player.getId(), player);
    }

    private void removePlayer(PlayerObject player) {
        players.remove(player.getId());
    }

    public Map<Integer, PlayerObject> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
