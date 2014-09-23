package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.actor.BQCreature;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQType;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BQSpawn {

    @LazyInject
    private ActorFactory actorFactory;
    @LazyInject
    private IdFactory idFactory;
    @LazyInject
    private BQWorld world;

    private BQType type;
    private Area area;

    private int maximumCount;

    private Set<BQObject> objects = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public BQSpawn(RoamingAreaTemplate template) {
        type = BQType.fromString(template.getType());
        area = new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight());
        maximumCount = template.getNb();
    }

    public void spawnAll() {
        for (int i = 0; i < maximumCount; i++) {
            doSpawn();
        }
    }

    public BQCreature doSpawn() {
        BQCreature creature = actorFactory.newActor(BQCreature.class);
        creature.setId(idFactory.getNextId());

        creature.setType(type);
        creature.setName(type.name());
        creature.setWorld(world);
        creature.setSpawn(this);

        creature.setPosition(PositionUtil.getRandomPositionInside(area));
        creature.setHeading(PositionUtil.getRandomHeading());

        world.addObject(creature);
        creature.getPositionController().spawnMe();

        objects.add(creature);
        return creature;
    }
}
