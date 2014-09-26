package org.mozilla.browserquest.model.status;

import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface StatusEventListener {

    void onHeal(BQCharacter healer, int amount);

    void onDamage(BQCharacter attacker, int amount);

    void onRevive();

    void onDie(BQCharacter killer);
}
