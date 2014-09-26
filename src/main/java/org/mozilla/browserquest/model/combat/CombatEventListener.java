package org.mozilla.browserquest.model.combat;

import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface CombatEventListener {

    void onAttack(BQCharacter attacker, int damage);
}
