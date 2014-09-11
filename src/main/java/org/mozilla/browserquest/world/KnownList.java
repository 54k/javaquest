package org.mozilla.browserquest.world;

import org.mozilla.browserquest.model.Character;
import org.mozilla.browserquest.model.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KnownList {

    private final int visibilityDistance = 25;

    private Character owner;
    private Map<Integer, Entity> knownObjects = new HashMap<>();

    public KnownList(Character owner) {
        this.owner = owner;
    }

    public void add(Entity entity) {
        if (knownObjects.put(entity.getId(), entity) == null) {
            if (entity instanceof Character) {
                Character ch = (Character) entity;
                owner.see(ch);
                ch.getKnownList().add(owner);
            }
        }
    }

    public void remove(Entity entity) {
        if (knownObjects.remove(entity.getId()) != null) {
            if (entity instanceof Character) {
                Character ch = (Character) entity;
                owner.notSee(ch);
                ch.getKnownList().remove(owner);
            }
        }
    }

    public void clear() {
        Iterator<Entity> iterator = knownObjects.values().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            iterator.remove();
            if (entity instanceof Character) {
                Character ch = (Character) entity;
                ch.getKnownList().remove(owner);
            }
        }
    }

    public void update() {
        forgetInvisibleObjects();
        findVisibleObjects();
    }

    private void forgetInvisibleObjects() {
        Iterator<Entity> iterator = knownObjects.values().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (checkObjectInRange(entity)) {
                continue;
            }

            iterator.remove();
            if (entity instanceof Character) {
                Character ch = (Character) entity;
                owner.notSee(ch);
                ch.getKnownList().remove(owner);
            }
        }
    }

    private void findVisibleObjects() {
        Collection<Entity> entities = owner.getWorldRegion().getEntities().values();
        for (Entity entity : entities) {
            if (entity == owner || !checkObjectInRange(entity)) {
                continue;
            }
            add(entity);
        }
    }

    private boolean checkObjectInRange(Entity entity) {
        return Math.abs(entity.getX() - owner.getX()) <= visibilityDistance && Math.abs(entity.getY() - owner.getY()) <= visibilityDistance;
    }

    public Iterable<Entity> getKnownEntities() {
        return knownObjects.values();
    }
}
