package org.mozilla.browserquest.gameserver.model.combat;

import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;

import java.util.Map;

public interface CombatController {

    BQCharacter getTarget();

    void setTarget(BQCharacter target);

    Map<Integer, BQCharacter> getAttackers();

    void addAttacker(BQCharacter attacker);

    void removeAttacker(BQCharacter attacker);

    void clearAttackers();

    void attack(BQCharacter target);
}
