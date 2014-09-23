package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSpawnService implements SpawnService {

    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    @Inject
    public DefaultSpawnService(List<RoamingAreaTemplate> roamingAreaTemplates) {
        load(roamingAreaTemplates);
    }

    private void load(List<RoamingAreaTemplate> roamingAreaTemplates) {
        for (RoamingAreaTemplate template : roamingAreaTemplates) {
            BQSpawn spawn = new BQSpawn(template);
            spawns.put(template.getId(), spawn);
            spawn.spawnAll();
        }
    }
}
