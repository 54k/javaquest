package org.mozilla.browserquest.world;

import com.google.inject.Inject;
import org.vertx.java.core.file.FileSystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class World {

    private Set<WorldInstance> worlds = new HashSet<>();

    private FileSystem fileSystem;

    @Inject
    public World(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void populateWorlds(int worldCount, int maxPlayers) {
        for (int i = 0; i < worldCount; i++) {
            WorldInstance worldInstance = new WorldInstance("world-" + (i + 1), maxPlayers);
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
}
