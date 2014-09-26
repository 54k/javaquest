package org.mozilla.browserquest.model.position;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;

@BehaviorPrototype(PositionController.class)
public class PositionControllerBehavior extends Behavior<BQObject> implements PositionController {

    private Position position = new Position();
    private BQWorld world;
    private BQWorldRegion region;

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position.setPosition(position);
    }

    @Override
    public void setPosition(int x, int y) {
        position.setXY(x, y);
    }

    @Override
    public BQWorld getWorld() {
        return world;
    }

    @Override
    public void setWorld(BQWorld world) {
        this.world = world;
    }

    @Override
    public BQWorldRegion getRegion() {
        return region;
    }

    @Override
    public boolean isSpawned() {
        return region != null;
    }

    @Override
    public void updatePosition(Position position) {
        Preconditions.checkNotNull(position);
        updatePosition(position.getX(), position.getY());
    }

    @Override
    public void updatePosition(int x, int y) {
        Preconditions.checkState(isSpawned());
        position.setXY(x, y);
        updateRegion();
    }

    private void updateRegion() {
        BQObject actor = getActor();

        BQWorldRegion oldRegion = region;
        BQWorldRegion newRegion = world.findRegion(actor.getPosition());

        if (oldRegion != newRegion) {
            oldRegion.removeObject(actor);
            region = newRegion;
            newRegion.addObject(actor);
        }
        actor.getKnownListController().updateKnownList();
        actor.post(PositionEventListener.class).onPositionChange();
    }

    @Override
    public void spawn() {
        Preconditions.checkState(!isSpawned());
        BQObject actor = getActor();
        BQWorldRegion region = world.findRegion(actor.getPosition());
        region.addObject(actor);
        actor.setRegion(region);
        actor.getKnownListController().updateKnownList();
        actor.post(SpawnEventListener.class).onSpawn();
    }

    @Override
    public void decay() {
        Preconditions.checkState(isSpawned());
        BQObject actor = getActor();
        region.removeObject(actor);
        actor.getKnownListController().clearKnownList();
        actor.setRegion(null);
        actor.post(SpawnEventListener.class).onDecay();
    }
}
