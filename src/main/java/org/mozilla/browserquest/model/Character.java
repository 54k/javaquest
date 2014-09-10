package org.mozilla.browserquest.model;

import java.util.HashMap;
import java.util.Map;

public class Character extends Entity {

    private Map<Integer, Entity> attackers = new HashMap<>();
    private Entity target;

    private int maxHitPoints;
    private int hitPoints;

    public Character(int id, String type, String kind, int x, int y) {
        super(id, type, kind, x, y);
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
}
