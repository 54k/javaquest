package org.mozilla.browserquest.model.actor.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;
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
        updateRegions();
        Position position = activeObject.getPosition();

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.MOVE);
        jsonArray.addNumber(activeObject.getId());   //id
        jsonArray.addNumber(position.getX());   //x
        jsonArray.addNumber(position.getY());      //y

        Broadcast.toKnownPlayers(activeObject, jsonArray.encode());
    }

    public void updateRegions() {
        BQObject object = getActor();
        BQWorldRegion oldRegion = object.getRegion();
        BQWorldRegion newRegion = object.getWorld().getRegion(object.getPosition());

        if (oldRegion != newRegion) {
            oldRegion.removeObject(object);
            object.setRegion(newRegion);
            newRegion.addObject(object);
        }

        object.getKnownList().updateKnownObjects();
    }
}
