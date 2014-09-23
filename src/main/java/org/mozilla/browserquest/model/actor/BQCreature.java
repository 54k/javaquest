package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.controller.CreatureControllerBehavior;

@ActorPrototype(CreatureControllerBehavior.class)
public abstract class BQCreature extends BQCharacter {

    private BQSpawn spawn;

    public BQSpawn getSpawn() {
        return spawn;
    }

    public void setSpawn(BQSpawn spawn) {
        this.spawn = spawn;
    }

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 7;
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 5;
    }
}
