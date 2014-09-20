package org.mozilla.browserquest.model.actor.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;

@Prototype(Positionable.class)
public class PositionableBehavior extends Behavior<BQObject> implements Positionable {

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

        actor.getKnownList().updateKnownList();
    }

    public void spawnMe() {
        BQObject actor = getActor();

        assert actor.getRegion() == null;

        BQWorldRegion region = actor.getWorld().findRegion(actor.getPosition());
        actor.setRegion(region);
        region.addObject(actor);
        actor.getKnownList().updateKnownList();
        onSpawn();
    }

    public void onSpawn() {
    }

    public void decayMe() {
        BQObject actor = getActor();

        assert actor.getRegion() != null;

        BQWorldRegion region = actor.getRegion();
        region.removeObject(actor);
        actor.getKnownList().clearKnownList();
        actor.setRegion(null);
        onDecay();
    }

    public void onDecay() {
    }
}