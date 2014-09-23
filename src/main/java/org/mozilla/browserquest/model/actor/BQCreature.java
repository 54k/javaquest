package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.controller.CreatureControllerBehavior;

@ActorPrototype(CreatureControllerBehavior.class)
public abstract class BQCreature extends BQCharacter {

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 7;
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 5;
    }
}
