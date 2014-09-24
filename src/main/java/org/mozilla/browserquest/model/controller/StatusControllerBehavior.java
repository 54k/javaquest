package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.event.StatusListener;

@BehaviorPrototype(StatusController.class)
public class StatusControllerBehavior extends Behavior<BQCharacter> implements StatusController {

    @Override
    public void reduceHitPoints(BQCharacter attacker, int damage) {
        BQCharacter actor = getActor();
        int newHitPoints = actor.getHitPoints() - damage;
        if (newHitPoints <= 0) {
            actor.setDead(true);
            actor.post(StatusListener.class).onDie(attacker);
        }
        actor.setHitPoints(newHitPoints);
    }
}
