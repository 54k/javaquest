package org.mozilla.browserquest.gameserver.model.position;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.WorldMapInstance;
import org.mozilla.browserquest.gameserver.model.WorldMapRegion;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;

@ComponentPrototype(PositionController.class)
public class PositionControllerComponent extends Component<BaseObject> implements PositionController {

    private Position position = new Position();
    private Orientation orientation;

    private WorldMapInstance world;
    private WorldMapRegion region;

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
    public WorldMapInstance getWorldMapInstance() {
        return world;
    }

    @Override
    public void setWorldMapInstance(WorldMapInstance world) {
        this.world = world;
    }

    @Override
    public WorldMapRegion getRegion() {
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

        WorldMapRegion oldRegion = region;
        WorldMapRegion newRegion = world.findRegion(position);

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
        world.addObject(actor);
        WorldMapRegion newRegion = world.findRegion(position);
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
        world.removeObject(actor);
        actor.post(PositionEventListener.class).onDecay();
    }
}
