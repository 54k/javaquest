package org.mozilla.browserquest.world;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.json.JsonArray;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class World {

    private Set<WorldInstance> worlds = new HashSet<>();

    private FileSystem fileSystem;

    private Set<BQPlayer> BQPlayers = new HashSet<>();

    @Inject
    public World(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void populateWorlds(int worldCount, int maxPlayers) {
        for (int i = 0; i < worldCount; i++) {
            WorldInstance worldInstance = new WorldInstance(this, "world-" + (i + 1), maxPlayers);
            WorldMap worldMap = new WorldMap(fileSystem, "world_map.json");
            worldInstance.run(worldMap);
            worlds.add(worldInstance);
        }
    }

    public Set<WorldInstance> getWorldInstances() {
        return Collections.unmodifiableSet(worlds);
    }

    public WorldInstance getAvailableWorldInstance() {
        return worlds.stream().filter(world -> world.getPlayersCount() < world.getMaxPlayers()).findFirst().get();
    }

    public void addPlayer(BQPlayer BQPlayer) {
        BQPlayers.add(BQPlayer);
    }

    public void removePlayer(BQPlayer BQPlayer) {
        BQPlayers.remove(BQPlayer);
    }

    public void broadcastWorldPopulation() {
        BQPlayers.forEach(p -> {
            JsonArray populationPacket = new JsonArray();
            populationPacket.addNumber(Packet.POPULATION);
            populationPacket.addNumber(p.getWorldInstance().getPlayersCount());
            populationPacket.addNumber(BQPlayers.size());
            p.getConnection().write(populationPacket.encode());
        });
    }
}
