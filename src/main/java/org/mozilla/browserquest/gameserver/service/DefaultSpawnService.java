package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.gameserver.model.BQSpawn;
import org.mozilla.browserquest.gameserver.model.BQType;
import org.mozilla.browserquest.gameserver.model.BQWorld;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.service.ObjectFactory;
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
    @LazyInject
    private ObjectFactory objectFactory;


    private Map<Integer, BQSpawn> spawns = new ConcurrentHashMap<>();

    public DefaultSpawnService() {
        loadCreatureSpawns(dataService.getWorldTemplate().getRoamingAreas());
        loadStaticObjects(dataService.getWorldTemplate().getStaticEntities());
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
            } else {
                Position position = world.findPositionFromTileIndex(entry.getKey());
                BQType type = BQType.fromString(entry.getValue());

                BQObject staticObject = objectFactory.createObject(type.getPrototype());
                staticObject.setType(type);
                PositionController positionController = staticObject.getPositionController();
                positionController.setOrientation(Orientation.BOTTOM);
                positionController.setPosition(position);
                positionController.setWorld(world);
                world.addObject(staticObject);
                positionController.spawn();
            }
        }
    }
}
