package org.mozilla.browserquest.world;

import org.mozilla.browserquest.MobArea;
import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.map.MapRoamingArea;
import org.mozilla.browserquest.model.Character;
import org.mozilla.browserquest.model.Entity;
import org.mozilla.browserquest.model.Mob;
import org.mozilla.browserquest.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorldInstance {

    private World world;

    private String name;
    private int maxPlayers;
    private int playersCount;

    private WorldMap worldMap;

    private List<MobArea> mobAreas = new ArrayList<>();

    private Map<Integer, Entity> entities = new HashMap<>();
    private Set<Player> players = new HashSet<>();

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

    public void spawnEntity(Entity entity) {
        entities.put(entity.getId(), entity);
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;
            mob.setWorldInstance(this);
            updateCharacterRegionAndKnownList(mob);
        }
    }

    public void despawnEntity(Entity entity) {
        entities.remove(entity.getId());
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;
            mob.setWorldInstance(null);
            mob.getKnownList().clear();
            mob.setWorldRegion(null);
        }
    }

    public boolean addPlayer(Player player) {
        if (players.add(player)) {
            world.addPlayer(player);
            player.setWorldInstance(this);
            playersCount++;
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        if (players.remove(player)) {
            world.removePlayer(player);
            player.setWorldInstance(null);

            WorldRegion worldRegion = player.getWorldRegion();
            if (worldRegion != null) {
                worldRegion.removeEntity(player);
                player.setWorldRegion(null);
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
    }

    private void initMobAreas(List<MapRoamingArea> roamingAreas) {
        roamingAreas.forEach(a -> {
            mobAreas.add(new MobArea(a.getId(), a.getX(), a.getY(), a.getWidth(), a.getHeight(), this, a.getNb(), a.getType()));
        });
    }

    private void spawnMobs() {
        mobAreas.forEach(MobArea::spawnMobs);
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

    public void updateCharacterRegionAndKnownList(Character character) {
        WorldRegion oldWorldRegion = character.getWorldRegion();
        WorldRegion newWorldRegion = getRegion(character.getX(), character.getY());
        if (oldWorldRegion != newWorldRegion) {
            if (oldWorldRegion != null) {
                oldWorldRegion.removeEntity(character);
            }
            newWorldRegion.addEntity(character);

            character.setWorldRegion(newWorldRegion);
        }

        character.getKnownList().update();
    }
}
