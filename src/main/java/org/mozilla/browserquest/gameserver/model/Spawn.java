package org.mozilla.browserquest.gameserver.model;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CreatureObject;
import org.mozilla.browserquest.gameserver.model.actor.InstanceType;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.gameserver.model.status.StatusController;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.util.PositionUtil;
import org.mozilla.browserquest.util.RandomUtils;
import org.vertx.java.core.Vertx;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Spawn {

    @LazyInject
    private Vertx vertx;
    @LazyInject
    private ObjectFactory objectFactory;

    private WorldMapInstance worldMapInstance;

    private InstanceType type;
    private CreatureTemplate template;

    private Area area;

    private int minRespawnDelay;
    private int maxRespawnDelay;

    private boolean respawnEnabled = true;

    private int maxSpawns;
    private int pendingSpawns;
    private Set<BaseObject> spawnedCreatures = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public Spawn(CreatureTemplate template) {
        this.template = template;
    }

    public WorldMapInstance getWorldMapInstance() {
        return worldMapInstance;
    }

    public void setWorldMapInstance(WorldMapInstance worldMapInstance) {
        this.worldMapInstance = worldMapInstance;
    }

    public InstanceType getType() {
        return type;
    }

    public void setType(InstanceType type) {
        this.type = type;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getMinRespawnDelay() {
        return minRespawnDelay;
    }

    public void setMinRespawnDelay(int minRespawnDelay) {
        this.minRespawnDelay = minRespawnDelay;
    }

    public int getMaxRespawnDelay() {
        return maxRespawnDelay;
    }

    public void setMaxRespawnDelay(int maxRespawnDelay) {
        this.maxRespawnDelay = maxRespawnDelay;
    }

    public boolean isRespawnEnabled() {
        return respawnEnabled;
    }

    public void setRespawnEnabled(boolean respawnEnabled) {
        this.respawnEnabled = respawnEnabled;
    }

    public int getMaxSpawns() {
        return maxSpawns;
    }

    public void setMaxSpawns(int maxSpawns) {
        this.maxSpawns = maxSpawns;
    }

    public void spawnAll() {
        for (int i = 0; i < maxSpawns; i++) {
            spawn();
        }
    }

    public CreatureObject spawn() {
        CreatureObject creature = objectFactory.createObject(CreatureObject.class);
        creature.setInstanceType(type);
        creature.setName(type.name());
        creature.setTemplate(template);

        creature.getPositionController().setWorldMapInstance(worldMapInstance);
        creature.setSpawn(this);
        worldMapInstance.addObject(creature);
        return doSpawn(creature);
    }

    private CreatureObject doSpawn(CreatureObject creature) {
        PositionController positionController = creature.getPositionController();

        positionController.setPosition(getRandomSpawnPosition());
        positionController.setOrientation(PositionUtil.getRandomOrientation());
        StatusController statusController = creature.getStatusController();

        statusController.setHitPoints(creature.getStatsController().getMaxHitPoints());
        statusController.setDead(false);

        positionController.spawn();
        spawnedCreatures.add(creature);
        return creature;
    }

    private Position getRandomSpawnPosition() {
        Position spawnPosition;
        do {
            spawnPosition = PositionUtil.getRandomPositionInside(area);
        } while (worldMapInstance.getWorldMap().isColliding(spawnPosition.getX(), spawnPosition.getY()));
        return spawnPosition;
    }

    public void respawn(CreatureObject creature) {
        if (!spawnedCreatures.remove(creature)) {
            return;
        }

        if ((pendingSpawns + spawnedCreatures.size()) < maxSpawns) {
            pendingSpawns++;
            vertx.setTimer(RandomUtils.getRandomBetween(minRespawnDelay, maxRespawnDelay), l -> doRespawn(creature));
        }
    }

    private void doRespawn(CreatureObject creature) {
        if (respawnEnabled) {
            doSpawn(creature);
        }
        pendingSpawns--;
    }
}
