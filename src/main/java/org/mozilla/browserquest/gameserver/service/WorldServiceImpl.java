package org.mozilla.browserquest.gameserver.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.gameserver.model.WorldMap;
import org.mozilla.browserquest.gameserver.model.WorldMapInstance;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.network.packet.server.PopulationPacket;
import org.mozilla.browserquest.template.WorldMapTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorldServiceImpl implements WorldService {

    @Inject
    private SpawnService spawnService;

    private Map<Integer, BaseObject> objects = new HashMap<>();
    private Map<Integer, PlayerObject> players = new HashMap<>();

    private WorldMap worldMap;

    @Inject
    public WorldServiceImpl(WorldMapTemplate worldMapTemplate) {
        worldMap = new WorldMap(worldMapTemplate);
    }

    @Override
    public void addObject(BaseObject object) {
        if (object instanceof PlayerObject) {
            addPlayer((PlayerObject) object);
        }
        objects.put(object.getId(), object);
    }

    private void addPlayer(PlayerObject player) {
        players.put(player.getId(), player);
    }

    @Override
    public void removeObject(BaseObject object) {
        if (object instanceof PlayerObject) {
            removePlayer((PlayerObject) object);
        }
        objects.remove(object.getId(), object);
    }

    private void removePlayer(PlayerObject player) {
        players.remove(player.getId());
    }

    @Override
    public void broadcastPopulation() {
        for (PlayerObject playerObject : players.values()) {
            int instanceCount = playerObject.getPositionController().getWorldMapInstance().getPlayers().size();
            int totalCount = players.size();
            playerObject.getConnection().write(new PopulationPacket(instanceCount, totalCount));
        }
    }

    @Override
    public BaseObject findObject(int id) {
        return objects.get(id);
    }

    @Override
    public Map<Integer, BaseObject> getObjects() {
        return Collections.unmodifiableMap(players);
    }

    @Override
    public Map<Integer, PlayerObject> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    @Override
    public WorldMapInstance createWorldMapInstance(int maxPlayers) {
        WorldMapInstance worldMapInstance = worldMap.createWorldMapInstance(maxPlayers);
        spawnService.spawnCreatures(worldMapInstance);
        spawnService.spawnStaticObjects(worldMapInstance);
        return worldMapInstance;
    }

    @Override
    public Map<Integer, WorldMapInstance> getWorldMapInstances() {
        return worldMap.getWorldMapInstances();
    }

    @Override
    public WorldMapInstance getAvailableWorldMapInstance() {
        return worldMap.getWorldMapInstances().values().stream().findFirst().get();
    }
}
