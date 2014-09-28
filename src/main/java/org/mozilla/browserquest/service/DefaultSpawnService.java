package org.mozilla.browserquest.service;

import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.Area;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSpawnService implements SpawnService {

    @LazyInject
    private DataService dataService;
    @LazyInject
    private BQWorld world;

    private Map<Integer, Area> startingAreas = new ConcurrentHashMap<>();
    private Map<Integer, Area> spawnAreas = new ConcurrentHashMap<>();

    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    public DefaultSpawnService() {
        loadCheckpoints(dataService.getWorldTemplate().getCheckpoints());
        loadCreatureSpawns(dataService.getWorldTemplate().getRoamingAreas());
        loadStaticObjects(dataService.getWorldTemplate().getStaticEntities());
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

    private void loadStaticObjects(Map<Integer, String> staticObjects) {
        for (Map.Entry<Integer, String> entry : staticObjects.entrySet()) {
            CreatureTemplate creatureTemplate = dataService.getCreatureTemplates().get(entry.getValue());
            if (creatureTemplate != null) {
                BQSpawn spawn = new BQSpawn(creatureTemplate);
                Position position = world.findPositionFromTileIndex(entry.getKey());
                Area area = new Area(position.getX(), position.getY(), 0, 0);
                spawn.setArea(area);
                spawn.setMaxSpawns(1);
                spawn.setMaxRespawnDelay(30 * 1000);
                spawn.setMinRespawnDelay(10 * 1000);
                spawn.setType(BQType.fromString(entry.getValue()));
                spawn.spawnAll();
            }
        }
    }
}
