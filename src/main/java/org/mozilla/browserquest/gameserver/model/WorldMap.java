package org.mozilla.browserquest.gameserver.model;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.mozilla.browserquest.template.WorldMapTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WorldMap {

    private WorldMapTemplate worldMapTemplate;

    private Map<Integer, Area> playerStartingAreas = new HashMap<>();
    private Map<Integer, Area> playerSpawnAreas = new HashMap<>();

    private Map<Position, String> staticObjectSpawns = new HashMap<>();

    private int[][] collisionGrid;

    private int instanceId;
    private Map<Integer, WorldMapInstance> worldMapInstances = new HashMap<>();

    public WorldMap(WorldMapTemplate worldMapTemplate) {
        this.worldMapTemplate = worldMapTemplate;
        initPlayerSpawns();
        initCollisionGrid();
        initStaticObjects();
    }

    private void initPlayerSpawns() {
        for (CheckpointTemplate checkpoint : worldMapTemplate.getCheckpoints()) {
            Area area = new Area(checkpoint.getX(), checkpoint.getY(), checkpoint.getW(), checkpoint.getH());
            if (checkpoint.getS() == 1) {
                playerStartingAreas.put(checkpoint.getId(), area);
            } else {
                playerSpawnAreas.put(checkpoint.getId(), area);
            }
        }
    }

    private void initCollisionGrid() {
        int tileIndex = 0;
        int height = worldMapTemplate.getHeight();
        int width = worldMapTemplate.getWidth();

        collisionGrid = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                collisionGrid[i][j] = worldMapTemplate.getCollisions().contains(tileIndex) ? 1 : 0;
                tileIndex++;
            }
        }
    }

    private void initStaticObjects() {
        for (Entry<Integer, String> staticObjectEntry : worldMapTemplate.getStaticEntities().entrySet()) {
            staticObjectSpawns.put(getPositionFromTileIndex(staticObjectEntry.getKey()), staticObjectEntry.getValue());
        }
    }

    private Position getPositionFromTileIndex(int tileIndex) {
        int width = worldMapTemplate.getWidth();
        int x = (tileIndex % width == 0) ? width - 1 : (tileIndex % width);
        int y = (int) Math.floor(tileIndex / width);
        return new Position(x, y);
    }

    public WorldMapTemplate getWorldMapTemplate() {
        return worldMapTemplate;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x > worldMapTemplate.getWidth() || y < 0 || y > worldMapTemplate.getHeight();
    }

    public boolean isColliding(int x, int y) {
        return !isOutOfBounds(x, y) && collisionGrid[y][x] == 1;
    }

    public int getWidth() {
        return worldMapTemplate.getWidth();
    }

    public int getHeight() {
        return worldMapTemplate.getHeight();
    }

    public Map<Integer, Area> getPlayerStartingAreas() {
        return Collections.unmodifiableMap(playerStartingAreas);
    }

    public Map<Integer, Area> getPlayerSpawnAreas() {
        return Collections.unmodifiableMap(playerSpawnAreas);
    }

    public Map<Position, String> getStaticObjectSpawns() {
        return Collections.unmodifiableMap(staticObjectSpawns);
    }

    public WorldMapInstance createWorldMapInstance(int maxPlayers) {
        WorldMapInstance worldMapInstance = new WorldMapInstance(instanceId, this, maxPlayers);
        Preconditions.checkState(addWorldMapInstance(worldMapInstance));
        return worldMapInstance;
    }

    private boolean addWorldMapInstance(WorldMapInstance worldMapInstance) {
        if (worldMapInstances.put(worldMapInstance.getId(), worldMapInstance) == null) {
            instanceId++;
            return true;
        }
        return false;
    }

    public void destroyWorldMapInstance(WorldMapInstance worldMapInstance) {
        Preconditions.checkState(removeInstance(worldMapInstance));
    }

    private boolean removeInstance(WorldMapInstance worldMapInstance) {
        if (worldMapInstances.remove(worldMapInstance.getId()) != null) {
            instanceId++;
            return true;
        }
        return false;
    }

    public Map<Integer, WorldMapInstance> getWorldMapInstances() {
        return Collections.unmodifiableMap(worldMapInstances);
    }
}
