package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class PlayerKnownList extends ObjectKnownList {

    public PlayerKnownList(BQPlayer activeObject) {
        super(activeObject);
    }

    @Override
    public BQPlayer getActiveObject() {
        return (BQPlayer) super.getActiveObject();
    }

    @Override
    protected void onObjectAddedToKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.SPAWN);
        object.getInfo().forEach(spawnPacket::add);
        getActiveObject().getConnection().write(spawnPacket.encode());
    }

    @Override
    protected void onObjectRemovedFromKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.DESPAWN);
        spawnPacket.addNumber(object.getId());
        getActiveObject().getConnection().write(spawnPacket.encode());
    }

    @Override
    protected int getDistanceToForgetObject(BQObject object) {
        return 30;
    }

    @Override
    protected int getDistanceToFindObject(BQObject object) {
        return 25;
    }
}
