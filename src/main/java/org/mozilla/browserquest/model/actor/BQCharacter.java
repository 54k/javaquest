package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.behavior.MovableBehavior;
import org.mozilla.browserquest.model.projection.CharacterProjection;
import org.vertx.java.core.json.JsonArray;

import java.util.Collections;
import java.util.Set;

@Prototype(MovableBehavior.class)
public abstract class BQCharacter extends BQObject implements CharacterProjection {

    private Set<BQCharacter> attackers;
    private BQObject target;

    private int hitPoints;
    private int maxHitPoints;

    private boolean dead;

    public Set<BQCharacter> getAttackers() {
        return Collections.unmodifiableSet(attackers);
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

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getHeading().getValue()});
    }
}
