package org.mozilla.browserquest.gameserver.model.status;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface StatusEventListener {

    void onHeal(CharacterObject healer, int amount);

    void onDamage(CharacterObject attacker, int amount);

    void onRevive();

    void onDie(CharacterObject killer);
}
