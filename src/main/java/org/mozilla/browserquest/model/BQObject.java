package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.behavior.PositionableBehavior;

@Prototype(PositionableBehavior.class)
public abstract class BQObject extends Actor {

    private int objectId;

    protected BQObject() {
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
}
