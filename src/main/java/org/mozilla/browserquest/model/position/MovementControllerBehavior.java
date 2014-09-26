package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

@BehaviorPrototype(MovementController.class)
public class MovementControllerBehavior extends Behavior<BQCharacter> implements MovementController {

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
}
