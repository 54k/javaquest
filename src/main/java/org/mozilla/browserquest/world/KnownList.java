package org.mozilla.browserquest.world;

import org.mozilla.browserquest.model.Character;
import org.mozilla.browserquest.model.Entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KnownList {

    private final int visibilityDistance = 150;

    private Character owner;
    private Map<Integer, Entity> knownObjects = new HashMap<>();

    public KnownList(Character owner) {
        this.owner = owner;
    }

    public void add(Entity entity) {
        knownObjects.put(entity.getId(), entity);
        if (entity instanceof Character) {
            Character ch = (Character) entity;
            owner.see(ch);
            ch.see(owner);
        }
    }

    public void remove(Entity entity) {
        knownObjects.remove(entity.getId(), entity);
        if (entity instanceof Character) {
            Character ch = (Character) entity;
            owner.notSee(ch);
            ch.notSee(owner);
        }
    }

    public void update() {
        forgetAll();
        findVisibleObjects();
    }

    private void forgetAll() {
        Iterator<Entity> iterator = knownObjects.values().stream().filter(entity -> entity != owner && Math.abs(entity.getX() - owner.getX()) >= visibilityDistance && Math.abs(entity.getY() - owner.getY()) >= visibilityDistance).iterator();
        while (iterator.hasNext()) {
            remove(iterator.next());
        }
    }

    private void findVisibleObjects() {
        owner.getWorldRegion().getEntities().values().stream()
                .filter(entity -> entity != owner && Math.abs(entity.getX() - owner.getX()) <= visibilityDistance && Math.abs(entity.getY() - owner.getY()) <= visibilityDistance)
                .forEach(this::add);
    }

    public Iterable<Entity> getKnownEntities() {
        return knownObjects.values();
    }
}
