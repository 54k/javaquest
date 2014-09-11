package org.mozilla.browserquest.model;

import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

import java.util.HashSet;
import java.util.Set;

public class Mob extends Character {

    private Set<Entity> hateSet = new HashSet<>();

    public Mob(int id, String kind, int x, int y) {
        super(id, "mob", kind, x, y);
    }

    @Override
    public void see(Character character) {
        if (character instanceof Player) {
            Position positionNextTo = getPositionNextTo(character);
            setPosition(positionNextTo);
            getWorldInstance().updateCharacterRegionAndKnownList(this);

            JsonArray chatPacket = new JsonArray();
            chatPacket.addNumber(Packet.CHAT);
            chatPacket.addNumber(getId());
            chatPacket.addString("Arghhh!");

            ((Player) character).getConnection().write(chatPacket.encode());
        }
    }

    @Override
    public void notSee(Character character) {
        super.notSee(character);
    }
}
