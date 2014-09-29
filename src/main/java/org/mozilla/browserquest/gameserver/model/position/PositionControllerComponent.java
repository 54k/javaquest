package org.mozilla.browserquest.gameserver.model.position;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.World;
import org.mozilla.browserquest.gameserver.model.WorldRegion;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;

@ComponentPrototype(PositionController.class)
public class PositionControllerComponent extends Component<BaseObject> implements PositionController {

    private Position position = new Position();
    private Orientation orientation;

    private World world;
    private WorldRegion region;

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
    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public WorldRegion getRegion() {
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
        BaseObject actor = getActor();

        WorldRegion oldRegion = region;
        WorldRegion newRegion = world.findRegion(position);

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
        BaseObject actor = getActor();
        WorldRegion newRegion = world.findRegion(position);
        newRegion.addObject(actor);
        region = newRegion;
        actor.getKnownListController().updateKnownList();
        actor.post(PositionEventListener.class).onSpawn();
    }

    @Override
    public void decay() {
        Preconditions.checkState(isSpawned());
        BaseObject actor = getActor();
        region.removeObject(actor);
        actor.getKnownListController().clearKnownList();
        region = null;
        actor.post(PositionEventListener.class).onDecay();
    }
}
