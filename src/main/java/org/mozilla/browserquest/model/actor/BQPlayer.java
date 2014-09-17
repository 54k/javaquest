package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.MobTypes;
import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.network.NetworkConnection;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class BQPlayer extends BQCharacter {

    private boolean hasEnteredInGame;
    private String name;

    private NetworkConnection connection;

    public BQPlayer() {
        super(-1, "player", "", 0, 0);
    }

    public boolean isHasEnteredInGame() {
        return hasEnteredInGame;
    }

    public void setHasEnteredInGame(boolean hasEnteredInGame) {
        this.hasEnteredInGame = hasEnteredInGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NetworkConnection getConnection() {
        return connection;
    }

    public void setConnection(NetworkConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onObjectAddedToKnownList(BQObject BQCharacter) {
        if (BQCharacter instanceof BQPlayer) {
            BQPlayer BQPlayer = (BQPlayer) BQCharacter;

            JsonArray spawnPacket = new JsonArray();
            spawnPacket.addNumber(Packet.SPAWN);
            spawnPacket.addNumber(BQPlayer.getObjectId());   //id
            spawnPacket.addNumber(1);   //type
            spawnPacket.addNumber(BQPlayer.getX());   //x
            spawnPacket.addNumber(BQPlayer.getY());      //y
            spawnPacket.addString(BQPlayer.getName()); // name
            spawnPacket.addNumber(1); // orientation
            spawnPacket.addNumber(21); // armor
            spawnPacket.addNumber(60); // weapon

            getConnection().write(spawnPacket.encode());
        } else if (BQCharacter.getType().equals("mob")) {
            JsonArray spawnPacket = new JsonArray();
            spawnPacket.addNumber(Packet.SPAWN);
            spawnPacket.addNumber(BQCharacter.getObjectId());   //id
            spawnPacket.addNumber(MobTypes.getKindFromString(BQCharacter.getKind()));   //kind
            spawnPacket.addNumber(BQCharacter.getX());   //x
            spawnPacket.addNumber(BQCharacter.getY());      //y

            getConnection().write(spawnPacket.encode());
        }
    }

    @Override
    public void onObjectRemovedFromKnownList(BQObject BQCharacter) {
        JsonArray despawnPacket = new JsonArray();
        despawnPacket.addNumber(Packet.DESPAWN);
        despawnPacket.addNumber(BQCharacter.getId());   //id

        getConnection().write(despawnPacket.encode());
    }
}
