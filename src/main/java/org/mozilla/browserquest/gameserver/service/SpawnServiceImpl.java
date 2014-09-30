package org.mozilla.browserquest.gameserver.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.Spawn;
import org.mozilla.browserquest.gameserver.model.WorldMapInstance;
import org.mozilla.browserquest.gameserver.model.actor.InstanceType;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.template.RoamingAreaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class SpawnServiceImpl implements SpawnService {

    @Inject
    private DataService dataService;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Spawn> spawns = new ArrayList<>();

    @Override
    public void spawnCreatures(WorldMapInstance worldMapInstance) {
        for (RoamingAreaTemplate roamingAreaTemplate : worldMapInstance.getWorldMap().getWorldMapTemplate().getRoamingAreas()) {
            Spawn spawn = createCreatureSpawn(roamingAreaTemplate);
            spawn.setWorldMapInstance(worldMapInstance);
            spawn.spawnAll();
        }
    }

    private Spawn createCreatureSpawn(RoamingAreaTemplate roamingAreaTemplate) {
        Spawn spawn = new Spawn(dataService.getCreatureTemplates().get(roamingAreaTemplate.getType()));
        spawn.setArea(new Area(roamingAreaTemplate.getX(), roamingAreaTemplate.getY(), roamingAreaTemplate.getWidth(), roamingAreaTemplate.getHeight()));
        spawn.setMaxSpawns(roamingAreaTemplate.getNb());
        spawn.setMaxRespawnDelay(30 * 1000);
        spawn.setMinRespawnDelay(10 * 1000);
        spawn.setType(InstanceType.fromString(roamingAreaTemplate.getType()));

        spawns.add(spawn);
        return spawn;
    }

    @Override
    public void spawnStaticObjects(WorldMapInstance worldMapInstance) {
        for (Entry<Position, String> staticObjectEntry : worldMapInstance.getWorldMap().getStaticObjectSpawns().entrySet()) {
            if (dataService.getCreatureTemplates().get(staticObjectEntry.getValue()) != null) {
                Spawn spawn = createStaticObjectSpawn(staticObjectEntry.getKey(), staticObjectEntry.getValue());
                spawn.setWorldMapInstance(worldMapInstance);
                spawn.spawnAll();
            }
        }
    }

    private Spawn createStaticObjectSpawn(Position position, String type) {
        Spawn spawn = new Spawn(dataService.getCreatureTemplates().get(type));
        spawn.setArea(new Area(position.getX(), position.getY(), 0, 0));
        spawn.setMaxSpawns(1);
        spawn.setMaxRespawnDelay(30 * 1000);
        spawn.setMinRespawnDelay(10 * 1000);
        spawn.setType(InstanceType.fromString(type));

        spawns.add(spawn);
        return spawn;
    }

    //    private Spawn spawnCreatures(RoamingAreaTemplate template) {
    //        Spawn spawn = new Spawn(dataService.getCreatureTemplates().get(template.getType()));
    //        spawn.setArea(new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight()));
    //        spawn.setMaxSpawns(template.getNb());
    //        spawn.setMaxRespawnDelay(30 * 1000);
    //        spawn.setMinRespawnDelay(10 * 1000);
    //        spawn.setType(InstanceType.fromString(template.getType()));
    //        return spawn;
    //    }

    //    private void loadStaticObjects(Map<Integer, String> staticObjects) {
    //        for (Map.Entry<Integer, String> entry : staticObjects.entrySet()) {
    //            CreatureTemplate creatureTemplate = dataService.getCreatureTemplates().get(entry.getValue());
    //            if (creatureTemplate != null) {
    //                Spawn spawn = new Spawn(creatureTemplate);
    //                Position position = worldServiceImpl.findPositionFromTileIndex(entry.getKey());
    //                Area area = new Area(position.getX(), position.getY(), 0, 0);
    //                spawn.setArea(area);
    //                spawn.setMaxSpawns(1);
    //                spawn.setMaxRespawnDelay(30 * 1000);
    //                spawn.setMinRespawnDelay(10 * 1000);
    //                spawn.setType(InstanceType.fromString(entry.getValue()));
    //                spawn.spawnAll();
    //            } else {
    //                Position position = worldServiceImpl.findPositionFromTileIndex(entry.getKey());
    //                InstanceType type = InstanceType.fromString(entry.getValue());
    //
    //                BaseObject staticObject = objectFactory.createObject(type.getPrototype());
    //                staticObject.setInstanceType(type);
    //                PositionController positionController = staticObject.getPositionController();
    //                positionController.setOrientation(Orientation.BOTTOM);
    //                positionController.setPosition(position);
    //                positionController.setWorldMapInstance(worldServiceImpl);
    //                worldServiceImpl.addObject(staticObject);
    //                positionController.spawn();
    //            }
    //        }
    //    }
}
