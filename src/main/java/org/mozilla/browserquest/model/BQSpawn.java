package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.actor.BQCreature;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.mozilla.browserquest.util.PositionUtil;
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
    private Area area;

    private boolean respawn;

    private int maximumCount;
    private int pendingSpawns;
    private Set<BQObject> spawnedCreatures = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public BQSpawn(RoamingAreaTemplate template) {
        type = BQType.fromString(template.getType());
        area = new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight());
        maximumCount = template.getNb();
    }

    public int getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(int maximumCount) {
        this.maximumCount = maximumCount;
    }

    public boolean isRespawnEnabled() {
        return respawn;
    }

    public void stopRespawn() {
        respawn = false;
    }

    public void startRespawn() {
        respawn = true;
    }

    public void spawnAll() {
        for (int i = 0; i < maximumCount; i++) {
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
        creature.setHitPoints(creature.getMaxHitPoints());

        creature.getPositionController().spawnMe();
        spawnedCreatures.add(creature);
        return creature;
    }

    public void decreaseCount(BQCreature creature) {
        spawnedCreatures.remove(creature);
        if ((pendingSpawns + spawnedCreatures.size()) < maximumCount) {
            pendingSpawns++;
            vertx.setTimer(1000, l -> respawnCreature(creature));
        }
    }

    private void respawnCreature(BQCreature creature) {
        if (respawn) {
            doSpawn(creature);
        }
        pendingSpawns--;
    }
}
