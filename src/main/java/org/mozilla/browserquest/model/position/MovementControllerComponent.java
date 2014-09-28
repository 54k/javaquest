package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

@ComponentPrototype(MovementController.class)
public class MovementControllerComponent extends Component<BQCharacter> implements MovementController {

    @Override
    public void moveTo(int x, int y) {
        BQCharacter actor = getActor();
        PositionController positionController = actor.getPositionController();
        positionController.updatePosition(x, y);
        positionController.setOrientation(Orientation.BOTTOM);
        Position position = positionController.getPosition();

        JsonArray movePacket = new JsonArray(new Object[]{Packet.MOVE, actor.getId(), position.getX(), position.getY()});
        BroadcastUtil.toKnownPlayers(actor, movePacket.encode());
    }

    @Override
    public void moveTo(Position position) {
        moveTo(position.getX(), position.getY());
    }

    @Override
    public void moveTo(BQObject object) {
        moveTo(PositionUtil.getRandomPositionNear(object));
    }

    @Override
    public void teleportTo(int x, int y) {
        moveTo(x, y);
    }

    @Override
    public void teleportTo(Position position) {
        moveTo(position);
    }

    @Override
    public void teleportTo(BQObject object) {
        moveTo(object);
    }
}
