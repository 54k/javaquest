package org.mozilla.browserquest.model.actor.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.interfaces.Movable;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.Broadcast;
import org.vertx.java.core.json.JsonArray;

@Prototype(Movable.class)
public class MovableBehavior extends Behavior<BQObject> implements Movable {

    @Override
    public void moveTo(int x, int y) {
        BQObject activeObject = getActor();
        activeObject.setXY(x, y);

        Position position = activeObject.getPosition();

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.MOVE);
        jsonArray.addNumber(activeObject.getId());   //id
        jsonArray.addNumber(position.getX());   //x
        jsonArray.addNumber(position.getY());      //y

        Broadcast.toKnownPlayers(activeObject, jsonArray.encode());
    }
}
