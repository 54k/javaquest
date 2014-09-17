package org.mozilla.browserquest.map;

import java.util.List;
import java.util.Map;

public class MapData {

    private int width;
    private int height;

    private List<Integer> collisions;
    private List<MapDoor> doors;
    private List<MapCheckpoint> checkpoints;
    private List<MapChestArea> chestAreas;
    private List<MapRoamingArea> roamingAreas;
    private List<MapStaticChest> staticChests;

    private Map<Integer, String> staticEntities;
    private int tileSize;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Integer> getCollisions() {
        return collisions;
    }

    public List<MapDoor> getDoors() {
        return doors;
    }

    public List<MapCheckpoint> getCheckpoints() {
        return checkpoints;
    }

    public List<MapChestArea> getChestAreas() {
        return chestAreas;
    }

    public List<MapRoamingArea> getRoamingAreas() {
        return roamingAreas;
    }

    public List<MapStaticChest> getStaticChests() {
        return staticChests;
    }

    public Map<Integer, String> getStaticEntities() {
        return staticEntities;
    }

    public int getTileSize() {
        return tileSize;
    }
}
