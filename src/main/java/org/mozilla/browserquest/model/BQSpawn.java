package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQType;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BQSpawn {

    private ActorFactory actorFactory;
    private IdFactory idFactory;

    private BQWorld world;

    private Class<? extends BQObject> prototype;
    private BQType type;

    private Area area;

    private int maximumCount;

    private Set<BQObject> objects = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public BQSpawn(RoamingAreaTemplate template, ActorFactory actorFactory, IdFactory idFactory, BQWorld world) {
        prototype = BQCharacter.class;
        type = BQType.fromString(template.getType());
        area = new Area(template.getX(), template.getY(), template.getWidth(), template.getHeight());
        maximumCount = template.getNb();

        this.actorFactory = actorFactory;
        this.idFactory = idFactory;
        this.world = world;
    }

    public void spawnAll() {
        for (int i = 0; i < maximumCount; i++) {
            doSpawn();
        }
    }

    public void doSpawn() {
        BQObject object = actorFactory.newActor(prototype);
        object.setId(idFactory.getNextId());

        object.setType(type);
        object.setName(type.name());
        object.setWorld(world);
        world.addObject(object);
        object.setPosition(PositionUtil.getRandomPositionInside(area));
        object.setHeading(PositionUtil.getRandomHeading());
        object.getPositionController().spawnMe();

        objects.add(object);
    }
}
