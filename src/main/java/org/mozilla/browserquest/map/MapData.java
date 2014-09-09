package org.mozilla.browserquest.map;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private Map<String, String> staticEntities;
    @JsonProperty("tilesize")
    private int tileSize;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Integer> getCollisions() {
        return collisions;
    }

    public void setCollisions(List<Integer> collisions) {
        this.collisions = collisions;
    }

    public List<MapDoor> getDoors() {
        return doors;
    }

    public void setDoors(List<MapDoor> doors) {
        this.doors = doors;
    }

    public List<MapCheckpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<MapCheckpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public List<MapChestArea> getChestAreas() {
        return chestAreas;
    }

    public void setChestAreas(List<MapChestArea> chestAreas) {
        this.chestAreas = chestAreas;
    }

    public List<MapRoamingArea> getRoamingAreas() {
        return roamingAreas;
    }

    public void setRoamingAreas(List<MapRoamingArea> roamingAreas) {
        this.roamingAreas = roamingAreas;
    }

    public List<MapStaticChest> getStaticChests() {
        return staticChests;
    }

    public void setStaticChests(List<MapStaticChest> staticChests) {
        this.staticChests = staticChests;
    }

    public Map<String, String> getStaticEntities() {
        return staticEntities;
    }

    public void setStaticEntities(Map<String, String> staticEntities) {
        this.staticEntities = staticEntities;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
