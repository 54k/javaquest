package org.mozilla.browserquest.model.combat;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ComponentPrototype(CombatController.class)
public class CombatControllerComponent extends Component<BQCharacter> implements CombatController {

    private BQCharacter target;
    private Map<Integer, BQCharacter> attackers = new ConcurrentHashMap<>();

    @Override
    public BQCharacter getTarget() {
        return target;
    }

    @Override
    public void setTarget(BQCharacter target) {
        this.target = target;
    }

    @Override
    public Map<Integer, BQCharacter> getAttackers() {
        return Collections.unmodifiableMap(attackers);
    }

    @Override
    public void addAttacker(BQCharacter attacker) {
        attackers.put(attacker.getId(), attacker);
    }

    @Override
    public void removeAttacker(BQCharacter attacker) {
        attackers.remove(attacker.getId());
    }

    @Override
    public void clearAttackers() {
        attackers.clear();
    }

    @Override
    public void attack(BQCharacter target) {
        BQCharacter actor = getActor();
        Position position = PositionUtil.getRandomPositionNear(target);
        actor.getPositionController().updatePosition(position);

        int targetArmor = target.getInventoryController().getArmor();
        int weapon = actor.getInventoryController().getWeapon() + 5;

        target.post(CombatEventListener.class).onAttack(actor, weapon - targetArmor);
    }
}
