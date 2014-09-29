package org.mozilla.browserquest.gameserver.model.combat;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ComponentPrototype(CombatController.class)
public class CombatControllerComponent extends Component<CharacterObject> implements CombatController {

    private CharacterObject target;
    private Map<Integer, CharacterObject> attackers = new ConcurrentHashMap<>();

    @Override
    public CharacterObject getTarget() {
        return target;
    }

    @Override
    public void setTarget(CharacterObject target) {
        this.target = target;
    }

    @Override
    public Map<Integer, CharacterObject> getAttackers() {
        return Collections.unmodifiableMap(attackers);
    }

    @Override
    public void addAttacker(CharacterObject attacker) {
        attackers.put(attacker.getId(), attacker);
    }

    @Override
    public void removeAttacker(CharacterObject attacker) {
        attackers.remove(attacker.getId());
    }

    @Override
    public void clearAttackers() {
        attackers.clear();
    }

    @Override
    public void attack(CharacterObject target) {
        CharacterObject actor = getActor();
        Position position = PositionUtil.getRandomPositionNear(target);
        actor.getPositionController().updatePosition(position);

        int targetArmor = target.getInventoryController().getArmor();
        int weapon = actor.getInventoryController().getWeapon() + 5;

        target.post(CombatEventListener.class).onAttack(actor, weapon - targetArmor);
    }
}
