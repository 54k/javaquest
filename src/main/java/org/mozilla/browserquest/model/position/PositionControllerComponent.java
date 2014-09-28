package org.mozilla.browserquest.model.position;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;

@ComponentPrototype(PositionController.class)
public class PositionControllerComponent extends Component<BQObject> implements PositionController {

    private Position position = new Position();
    private Orientation orientation;

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
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
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
        BQWorldRegion newRegion = world.findRegion(position);

        if (oldRegion != newRegion) {
            oldRegion.removeObject(actor);
            region = newRegion;
            newRegion.addObject(actor);
        }
        actor.getKnownList().updateKnownList();
        actor.post(PositionEventListener.class).onPositionChange();
    }

    @Override
    public void spawn() {
        Preconditions.checkState(!isSpawned());
        BQObject actor = getActor();
        BQWorldRegion newRegion = world.findRegion(position);
        newRegion.addObject(actor);
        region = newRegion;
        actor.getKnownList().updateKnownList();
        actor.post(PositionEventListener.class).onSpawn();
    }

    @Override
    public void decay() {
        Preconditions.checkState(isSpawned());
        BQObject actor = getActor();
        region.removeObject(actor);
        actor.getKnownList().clearKnownList();
        region = null;
        actor.post(PositionEventListener.class).onDecay();
    }
}
