package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.network.NetworkConnection;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype
public abstract class BQPlayer extends BQCharacter {

    private NetworkConnection connection;

    public BQPlayer() {
        setType(BQType.WARRIOR);
    }

    public NetworkConnection getConnection() {
        return connection;
    }

    public void setConnection(NetworkConnection connection) {
        this.connection = connection;
    }

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getName(), getHeading().getValue(), 21, 60});
    }

    @Override
    public void onObjectAddedToKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.SPAWN);
        object.getInfo().forEach(spawnPacket::add);
        getConnection().write(spawnPacket.encode());
    }

    @Override
    public void onObjectRemovedFromKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.DESPAWN);
        spawnPacket.addNumber(object.getId());
        getConnection().write(spawnPacket.encode());
    }

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 8;
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 5;
    }
}
