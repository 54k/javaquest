package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.actor.BQCreature;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.util.PositionUtil;
import org.mozilla.browserquest.util.RandomUtils;
import org.vertx.java.core.Vertx;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BQSpawn {

    @LazyInject
    private Vertx vertx;
    @LazyInject
    private ActorFactory actorFactory;
    @LazyInject
    private IdFactory idFactory;
    @LazyInject
    private BQWorld world;

    private BQType type;
    private CreatureTemplate template;

    private Area area;

    private int minRespawnDelay;
    private int maxRespawnDelay;

    private boolean respawnEnabled = true;

    private int maxSpawns;
    private int pendingSpawns;
    private Set<BQObject> spawnedCreatures = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public BQSpawn(CreatureTemplate template) {
        this.template = template;
    }

    public BQType getType() {
        return type;
    }

    public void setType(BQType type) {
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
            spawnOne();
        }
    }

    public BQCreature spawnOne() {
        BQCreature creature = actorFactory.newActor(BQCreature.class);
        creature.setId(idFactory.getNextId());
        creature.setType(type);
        creature.setName(type.name());
        creature.setWorld(world);
        creature.setSpawn(this);
        world.addObject(creature);
        return doSpawn(creature);
    }

    private BQCreature doSpawn(BQCreature creature) {
        creature.setPosition(PositionUtil.getRandomPositionInside(area));
        creature.setHeading(PositionUtil.getRandomHeading());
        creature.setDead(false);

        creature.setMaxHitPoints(template.getHitPoints());
        creature.setHitPoints(template.getHitPoints());
        creature.setWeapon(template.getWeapon());
        creature.setArmor(template.getArmor());

        creature.getPositionController().spawnMe();
        spawnedCreatures.add(creature);
        return creature;
    }

    public void decreaseCount(BQCreature creature) {
        if (!spawnedCreatures.remove(creature)) {
            return;
        }

        if ((pendingSpawns + spawnedCreatures.size()) < maxSpawns) {
            pendingSpawns++;
            vertx.setTimer(RandomUtils.getRandomBetween(minRespawnDelay, maxRespawnDelay), l -> respawnCreature(creature));
        }
    }

    private void respawnCreature(BQCreature creature) {
        if (respawnEnabled) {
            doSpawn(creature);
        }
        pendingSpawns--;
    }
}
