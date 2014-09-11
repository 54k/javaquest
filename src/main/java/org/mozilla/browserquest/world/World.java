package org.mozilla.browserquest.world;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.Player;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.json.JsonArray;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class World {

    private Set<WorldInstance> worlds = new HashSet<>();

    private FileSystem fileSystem;

    private Set<Player> players = new HashSet<>();

    @Inject
    public World(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void populateWorlds(int worldCount, int maxPlayers) {
        for (int i = 0; i < worldCount; i++) {
            WorldInstance worldInstance = new WorldInstance(this, "world-" + (i + 1), maxPlayers);
            worldInstance.run(new WorldMap(fileSystem, "world_map.json"));
            worlds.add(worldInstance);
        }
    }

    public Set<WorldInstance> getWorldInstances() {
        return Collections.unmodifiableSet(worlds);
    }

    public WorldInstance getAvailableWorldInstance() {
        return worlds.stream().filter(world -> world.getPlayersCount() < world.getMaxPlayers()).findFirst().get();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void broadcastWorldPopulation() {
        players.forEach(p -> {
            JsonArray populationPacket = new JsonArray();
            populationPacket.addNumber(Packet.POPULATION);
            populationPacket.addNumber(p.getWorldInstance().getPlayersCount());
            populationPacket.addNumber(players.size());
            p.getConnection().write(populationPacket.encode());
        });
    }
}
