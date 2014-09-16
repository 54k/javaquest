package org.mozilla.browserquest.world;

import org.mozilla.browserquest.MobSpawnArea;
import org.mozilla.browserquest.MobTypes;
import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.map.MapRoamingArea;
import org.mozilla.browserquest.model.BQCharacter;
import org.mozilla.browserquest.model.BQMob;
import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.BQPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WorldInstance {

    private World world;

    private String name;
    private int maxPlayers;
    private int playersCount;

    private WorldMap worldMap;

    private List<MobSpawnArea> mobAreas = new ArrayList<>();

    private Map<Integer, BQObject> entities = new HashMap<>();
    private Set<BQPlayer> BQPlayers = new HashSet<>();

    private Map<String, WorldRegion> worldRegions = new HashMap<>();

    public WorldInstance(World world, String name, int maxPlayers) {
        this.world = world;
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public World getWorld() {
        return world;
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

    public void spawnEntity(BQObject BQEntity) {
        entities.put(BQEntity.getId(), BQEntity);
        if (BQEntity instanceof BQMob) {
            BQMob BQMob = (BQMob) BQEntity;
            BQMob.setWorldInstance(this);
            updateCharacterRegionAndKnownList(BQMob);
        }
    }

    public void despawnEntity(BQObject BQEntity) {
        entities.remove(BQEntity.getId());
        if (BQEntity instanceof BQMob) {
            BQMob BQMob = (BQMob) BQEntity;
            BQMob.setWorldInstance(null);
            BQMob.getKnownList().clearKnownObjects();
            BQMob.setWorldRegion(null);
        }
    }

    public boolean addPlayer(BQPlayer BQPlayer) {
        if (BQPlayers.add(BQPlayer)) {
            world.addPlayer(BQPlayer);
            BQPlayer.setWorldInstance(this);
            playersCount++;
            return true;
        }
        return false;
    }

    public boolean removePlayer(BQPlayer BQPlayer) {
        if (BQPlayers.remove(BQPlayer)) {
            world.removePlayer(BQPlayer);
            BQPlayer.setWorldInstance(null);

            WorldRegion worldRegion = BQPlayer.getWorldRegion();
            if (worldRegion != null) {
                worldRegion.removeEntity(BQPlayer);
                BQPlayer.setWorldRegion(null);
            }
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
        initMobAreas(map.getRoamingAreas());
        spawnMobs();
        spawnStaticEntities();
    }

    private void spawnStaticEntities() {
        int i = 0;
        for (Entry<Integer, String> entry : worldMap.getStaticEntities().entrySet()) {
            Position position = worldMap.getPositionFromTileIndex(entry.getKey());
            spawnEntity(new BQMob(9000 + i++, MobTypes.DEATHKNIGHT.name().toLowerCase(), position.getX(), position.getY()));
        }
    }

    private void initMobAreas(List<MapRoamingArea> roamingAreas) {
        roamingAreas.forEach(a -> {
            mobAreas.add(new MobSpawnArea(a.getId(), a.getX(), a.getY(), a.getWidth(), a.getHeight(), this, a.getNb(), a.getType()));
        });
    }

    private void spawnMobs() {
        mobAreas.forEach(MobSpawnArea::spawnMobs);
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

    public void updateCharacterRegionAndKnownList(BQCharacter BQCharacter) {
        WorldRegion oldWorldRegion = BQCharacter.getWorldRegion();
        WorldRegion newWorldRegion = getRegion(BQCharacter.getX(), BQCharacter.getY());
        if (oldWorldRegion != newWorldRegion) {
            if (oldWorldRegion != null) {
                oldWorldRegion.removeEntity(BQCharacter);
            }
            newWorldRegion.addEntity(BQCharacter);

            BQCharacter.setWorldRegion(newWorldRegion);
        }

        BQCharacter.getKnownList().updateKnownObjects();
    }
}
