package org.mozilla.browserquest.model;

import org.mozilla.browserquest.network.NetworkConnection;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class Player extends Character {

    private boolean hasEnteredInGame;
    private String name;

    private NetworkConnection connection;

    public Player() {
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
    public void see(Character character) {
        if (character instanceof Player) {
            Player player = (Player) character;

            JsonArray spawnPacket = new JsonArray();
            spawnPacket.addNumber(Packet.SPAWN);
            spawnPacket.addNumber(player.getId());   //id
            spawnPacket.addNumber(1);   //type
            spawnPacket.addNumber(player.getX());   //x
            spawnPacket.addNumber(player.getY());      //y
            spawnPacket.addString(player.getName()); // name
            spawnPacket.addNumber(1); // orientation
            spawnPacket.addNumber(21); // armor
            spawnPacket.addNumber(60); // weapon

            getConnection().write(spawnPacket.encode());
        } else {
            JsonArray spawnPacket = new JsonArray();
            spawnPacket.addNumber(Packet.SPAWN);
            spawnPacket.addNumber(character.getId());   //id
            spawnPacket.addNumber(5);   //type - OGRE
            spawnPacket.addNumber(character.getX());   //x
            spawnPacket.addNumber(character.getY());      //y

            getConnection().write(spawnPacket.encode());
        }
    }

    @Override
    public void notSee(Character character) {
        JsonArray despawnPacket = new JsonArray();
        despawnPacket.addNumber(Packet.DESPAWN);
        despawnPacket.addNumber(character.getId());   //id

        getConnection().write(despawnPacket.encode());
    }
}
