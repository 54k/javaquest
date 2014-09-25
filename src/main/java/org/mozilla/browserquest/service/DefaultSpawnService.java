package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.Area;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSpawnService implements SpawnService {

    @LazyInject
    private DataService dataService;

    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    @Inject
    public DefaultSpawnService(List<RoamingAreaTemplate> roamingAreaTemplates) {
        load(roamingAreaTemplates);
    }

    private void load(List<RoamingAreaTemplate> roamingAreaTemplates) {
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
