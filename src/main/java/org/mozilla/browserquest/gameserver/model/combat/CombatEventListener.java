package org.mozilla.browserquest.gameserver.model.combat;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface CombatEventListener {

    void onAttack(CharacterObject attacker, int damage);
}
