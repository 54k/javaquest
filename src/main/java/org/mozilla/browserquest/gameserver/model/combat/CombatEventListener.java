package org.mozilla.browserquest.gameserver.model.combat;

import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface CombatEventListener {

    void onAttack(BQCharacter attacker, int damage);
}
