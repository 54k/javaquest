package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.event.PositionListener;

@BehaviorPrototype(PositionController.class)
public class PositionControllerBehavior extends Behavior<BQObject> implements PositionController {

    @Override
    public void setX(int x) {
        BQObject actor = getActor();
        setXY(x, actor.getY());
    }

    @Override
    public void setY(int y) {
        BQObject actor = getActor();
        setXY(actor.getX(), y);
    }

    @Override
    public void setPosition(Position position) {
        setXY(position.getX(), position.getY());
    }

    @Override
    public void setXY(int x, int y) {
        BQObject actor = getActor();

        assert actor.getRegion() != null;

        actor.setX(x);
        actor.setY(y);
        updateRegion();
    }

    public void updateRegion() {
        BQObject actor = getActor();

        BQWorldRegion oldRegion = actor.getRegion();
        BQWorldRegion newRegion = actor.getWorld().findRegion(actor.getPosition());

        if (oldRegion != newRegion) {
            oldRegion.removeObject(actor);
            actor.setRegion(newRegion);
            newRegion.addObject(actor);
        }

        actor.getKnownListController().updateKnownList();
    }

    public void spawnMe() {
        BQObject actor = getActor();

        assert actor.getRegion() == null;

        BQWorldRegion region = actor.getWorld().findRegion(actor.getPosition());
        actor.setRegion(region);
        region.addObject(actor);
        actor.getKnownListController().updateKnownList();
        actor.post(PositionListener.class).onSpawn();
    }

    public void decayMe() {
        BQObject actor = getActor();

        assert actor.getRegion() != null;

        BQWorldRegion region = actor.getRegion();
        region.removeObject(actor);
        actor.getKnownListController().clearKnownList();
        actor.setRegion(null);
        actor.post(PositionListener.class).onDecay();
    }
}
