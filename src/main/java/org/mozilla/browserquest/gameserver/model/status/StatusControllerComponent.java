package org.mozilla.browserquest.gameserver.model.status;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;

@ComponentPrototype(StatusController.class)
public class StatusControllerComponent extends Component<BQCharacter> implements StatusController {

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
        if (isDead()) {
            return;
        }
        int newHitPoints = hitPoints - amount;
        if (newHitPoints <= 0) {
            die(attacker);
            return;
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
        hitPoints = 0;
        getActor().post(StatusEventListener.class).onDie(killer);
    }
}
