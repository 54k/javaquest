package org.mozilla.browserquest.model.interfaces;

import org.mozilla.browserquest.model.actor.BQCharacter;

public interface StatusController {

    void reduceHitPoints(BQCharacter attacker, int damage);
}
