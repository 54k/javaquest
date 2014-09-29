package org.mozilla.browserquest.gameserver.model.status;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;

@ComponentPrototype(StatusController.class)
public class StatusControllerComponent extends Component<CharacterObject> implements StatusController {

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
    public void heal(CharacterObject healer, int amount) {
        getActor().post(StatusEventListener.class).onHeal(healer, amount);
    }

    @Override
    public void damage(CharacterObject attacker, int amount) {
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
    public void die(CharacterObject killer) {
        setDead(true);
        hitPoints = 0;
        getActor().post(StatusEventListener.class).onDie(killer);
    }
}
