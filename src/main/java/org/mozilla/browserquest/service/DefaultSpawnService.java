package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.Area;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSpawnService implements SpawnService {

    @LazyInject
    private DataService dataService;

    private Map<Integer, Area> startingAreas = new ConcurrentHashMap<>();
    private Map<Integer, Area> spawnAreas = new ConcurrentHashMap<>();

    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    public DefaultSpawnService() {
        loadCheckpoints(dataService.getWorldTemplate().getCheckpoints());
        loadCreatureSpawns(dataService.getWorldTemplate().getRoamingAreas());
    }

    private void loadCheckpoints(List<CheckpointTemplate> checkpoints) {
        for (CheckpointTemplate checkpoint : checkpoints) {
            if (checkpoint.getS() == 1) {
                startingAreas.put(checkpoint.getId(), new Area(checkpoint.getX(), checkpoint.getY(), checkpoint.getW(), checkpoint.getH()));
            } else {
                spawnAreas.put(checkpoint.getId(), new Area(checkpoint.getX(), checkpoint.getY(), checkpoint.getW(), checkpoint.getH()));
            }
        }
    }

    private void loadCreatureSpawns(List<RoamingAreaTemplate> roamingAreaTemplates) {
        for (RoamingAreaTemplate template : roamingAreaTemplates) {
            BQSpawn spawn = createSpawn(template);
            spawns.put(template.getId(), spawn);
            spawn.spawnAll();
        }
    }

    private BQSpawn createSpawn(RoamingAreaTemplate template) {
        BQSpawn spawn = new BQSpawn(dataService.getCreatureTemplates().get(template.getType()));
        spawn.setArea(new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight()));
        spawn.setMaxSpawns(template.getNb());
        spawn.setMaxRespawnDelay(30 * 1000);
        spawn.setMinRespawnDelay(10 * 1000);
        spawn.setType(BQType.fromString(template.getType()));
        return spawn;
    }
}
