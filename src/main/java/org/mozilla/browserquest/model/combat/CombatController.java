package org.mozilla.browserquest.model.combat;

import org.mozilla.browserquest.model.actor.BQCharacter;

import java.util.Map;

public interface CombatController {

    BQCharacter getTarget();

    void setTarget(BQCharacter target);

    Map<Integer, BQCharacter> getAttackers();

    void addAttacker(BQCharacter attacker);

    void removeAttacker(BQCharacter attacker);

    void attack(BQCharacter target);
}
