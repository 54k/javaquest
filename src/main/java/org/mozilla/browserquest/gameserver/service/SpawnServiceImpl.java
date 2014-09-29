package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.Spawn;
import org.mozilla.browserquest.gameserver.model.World;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.InstanceType;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnServiceImpl implements SpawnService {

    @LazyInject
    private DataService dataService;
    @LazyInject
    private World world;
    @LazyInject
    private ObjectFactory objectFactory;

    private Map<Integer, Spawn> spawns = new ConcurrentHashMap<>();

    public SpawnServiceImpl() {
        loadCreatureSpawns(dataService.getWorldTemplate().getRoamingAreas());
        loadStaticObjects(dataService.getWorldTemplate().getStaticEntities());
    }

    private void loadCreatureSpawns(List<RoamingAreaTemplate> roamingAreaTemplates) {
        for (RoamingAreaTemplate template : roamingAreaTemplates) {
            Spawn spawn = createSpawn(template);
            spawns.put(template.getId(), spawn);
            spawn.spawnAll();
        }
    }

    private Spawn createSpawn(RoamingAreaTemplate template) {
        Spawn spawn = new Spawn(dataService.getCreatureTemplates().get(template.getType()));
        spawn.setArea(new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight()));
        spawn.setMaxSpawns(template.getNb());
        spawn.setMaxRespawnDelay(30 * 1000);
        spawn.setMinRespawnDelay(10 * 1000);
        spawn.setType(InstanceType.fromString(template.getType()));
        return spawn;
    }

    private void loadStaticObjects(Map<Integer, String> staticObjects) {
        for (Map.Entry<Integer, String> entry : staticObjects.entrySet()) {
            CreatureTemplate creatureTemplate = dataService.getCreatureTemplates().get(entry.getValue());
            if (creatureTemplate != null) {
                Spawn spawn = new Spawn(creatureTemplate);
                Position position = world.findPositionFromTileIndex(entry.getKey());
                Area area = new Area(position.getX(), position.getY(), 0, 0);
                spawn.setArea(area);
                spawn.setMaxSpawns(1);
                spawn.setMaxRespawnDelay(30 * 1000);
                spawn.setMinRespawnDelay(10 * 1000);
                spawn.setType(InstanceType.fromString(entry.getValue()));
                spawn.spawnAll();
            } else {
                Position position = world.findPositionFromTileIndex(entry.getKey());
                InstanceType type = InstanceType.fromString(entry.getValue());

                BaseObject staticObject = objectFactory.createObject(type.getPrototype());
                staticObject.setInstanceType(type);
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
