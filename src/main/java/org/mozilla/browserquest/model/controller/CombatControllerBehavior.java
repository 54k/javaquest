package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.event.CombatListener;
import org.mozilla.browserquest.util.PositionUtil;

@BehaviorPrototype(CombatController.class)
public class CombatControllerBehavior extends Behavior<BQCharacter> implements CombatController {

    @Override
    public void attackTarget(BQCharacter target) {
        BQCharacter actor = getActor();
        Position position = PositionUtil.getRandomPositionNear(target);
        actor.getPositionController().setPosition(position);

        int targetArmor = target.getArmor();
        int weapon = actor.getWeapon() + 5;

        target.post(CombatListener.class).onAttack(actor, weapon - targetArmor);
    }
}
