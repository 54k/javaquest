package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.model.actor.BQCharacter;

public interface CombatController {

    void attackTarget(BQCharacter target);

    void receiveDamage(BQCharacter attacker, int damage);
}
