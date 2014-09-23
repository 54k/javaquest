package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.controller.CombatControllerBehavior;
import org.mozilla.browserquest.model.controller.MovementControllerBehavior;
import org.mozilla.browserquest.model.controller.StatusControllerBehavior;
import org.mozilla.browserquest.model.projection.CharacterProjection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype({MovementControllerBehavior.class, CombatControllerBehavior.class, StatusControllerBehavior.class})
public abstract class BQCharacter extends BQObject implements CharacterProjection {

    private Map<Integer, BQCharacter> attackers = new ConcurrentHashMap<>();
    private BQCharacter target;

    private int hitPoints;
    private int maxHitPoints;

    private boolean dead;

    public Map<Integer, BQCharacter> getAttackers() {
        return attackers;
    }

    public BQCharacter getTarget() {
        return target;
    }

    public void setTarget(BQCharacter target) {
        this.target = target;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
