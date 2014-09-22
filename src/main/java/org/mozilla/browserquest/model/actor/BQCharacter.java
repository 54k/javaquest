package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.controller.CombatControllerBehavior;
import org.mozilla.browserquest.model.controller.MovementControllerBehavior;
import org.mozilla.browserquest.model.projection.CharacterProjection;
import org.vertx.java.core.json.JsonArray;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype({MovementControllerBehavior.class, CombatControllerBehavior.class})
public abstract class BQCharacter extends BQObject implements CharacterProjection {

    private Map<Integer, BQCharacter> attackers = new ConcurrentHashMap<>();
    private BQObject target;

    private int hitPoints;
    private int maxHitPoints;

    private boolean dead;

    public Map<Integer, BQCharacter> getAttackers() {
        return attackers;
    }

    public BQObject getTarget() {
        return target;
    }

    public void setTarget(BQObject target) {
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

    public void onRevive() {
    }

    public void onDie(BQCharacter killer) {
    }

    public void onAttack(BQCharacter attacker) {
    }

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getHeading().getValue()});
    }
}
