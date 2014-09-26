package org.mozilla.browserquest.model.status;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;

@BehaviorPrototype(StatusController.class)
public class StatusControllerBehavior extends Behavior<BQCharacter> implements StatusController {

    private int hitPoints;
    private boolean dead;

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void heal(BQCharacter healer, int amount) {
        getActor().post(StatusEventListener.class).onHeal(healer, amount);
    }

    @Override
    public void damage(BQCharacter attacker, int amount) {
        int newHitPoints = hitPoints - amount;
        if (newHitPoints <= 0) {
            die(attacker);
        }
        hitPoints = newHitPoints;
        getActor().post(StatusEventListener.class).onDamage(attacker, amount);
    }

    @Override
    public void revive() {
        setDead(false);
        getActor().post(StatusEventListener.class).onRevive();
    }

    @Override
    public void die(BQCharacter killer) {
        setDead(true);
        getActor().post(StatusEventListener.class).onDie(killer);
    }
}
