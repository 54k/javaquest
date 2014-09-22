package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;

@BehaviorPrototype(CombatController.class)
public class CombatControllerBehavior extends Behavior<BQCharacter> implements CombatController {

    @Override
    public void attack(BQCharacter character) {
    }
}
