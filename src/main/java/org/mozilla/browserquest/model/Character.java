package org.mozilla.browserquest.model;

import org.mozilla.browserquest.world.KnownList;
import org.mozilla.browserquest.world.WorldInstance;
import org.mozilla.browserquest.world.WorldRegion;

import java.util.HashMap;
import java.util.Map;

public class Character extends Entity {

    private WorldInstance worldInstance;
    private WorldRegion worldRegion;

    private Map<Integer, Entity> attackers = new HashMap<>();
    private Entity target;

    private int maxHitPoints;
    private int hitPoints;

    private KnownList knownList;

    public Character(int id, String type, String kind, int x, int y) {
        super(id, type, kind, x, y);
        knownList = new KnownList(this);
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public WorldRegion getWorldRegion() {
        return worldRegion;
    }

    public void setWorldRegion(WorldRegion worldRegion) {
        this.worldRegion = worldRegion;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void addAttacker(Entity attacker) {
        attackers.put(attacker.getId(), attacker);
    }

    public void removeAttacker(Entity attacker) {
        attackers.remove(attacker.getId());
    }

    public Map<Integer, Entity> getAttackers() {
        return attackers;
    }

    public void see(Character character) {
    }

    public void notSee(Character character) {
    }

    public KnownList getKnownList() {
        return knownList;
    }
}
