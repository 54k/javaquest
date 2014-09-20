package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSpawnService implements SpawnService {

    private ActorFactory actorFactory;
    private IdFactory idFactory;

    private BQWorld world;

    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    @Inject
    public DefaultSpawnService(ActorFactory actorFactory, IdFactory idFactory, BQWorld world, List<RoamingAreaTemplate> roamingAreaTemplates) {
        this.actorFactory = actorFactory;
        this.idFactory = idFactory;
        this.world = world;

        load(roamingAreaTemplates);
    }

    private void load(List<RoamingAreaTemplate> roamingAreaTemplates) {
        for (RoamingAreaTemplate template : roamingAreaTemplates) {
            BQSpawn spawn = new BQSpawn(template, actorFactory, idFactory, world);
            spawns.put(template.getId(), spawn);
            spawn.spawnAll();
        }
    }
}
