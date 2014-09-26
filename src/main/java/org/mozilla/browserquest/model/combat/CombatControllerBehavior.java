package org.mozilla.browserquest.model.combat;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@BehaviorPrototype(CombatController.class)
public class CombatControllerBehavior extends Behavior<BQCharacter> implements CombatController {

    private BQCharacter target;
    private Map<Integer, BQCharacter> attackers = new ConcurrentHashMap<>();

    @Override
    public void attackTarget(BQCharacter target) {
        BQCharacter actor = getActor();
        Position position = PositionUtil.getRandomPositionNear(target);
        actor.getPositionController().updatePosition(position);

        int targetArmor = target.getArmor();
        int weapon = actor.getWeapon() + 5;

        target.post(CombatEventListener.class).onAttack(actor, weapon - targetArmor);
    }
}
