package org.mozilla.browserquest.model.event;

import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface CombatListener {

    void onAttack(BQCharacter attacker, int damage);
}
