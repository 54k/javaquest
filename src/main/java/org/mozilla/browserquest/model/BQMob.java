package org.mozilla.browserquest.model;

import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

import java.util.HashSet;
import java.util.Set;

public class BQMob extends BQCharacter {

    private Set<BQObject> hateSet = new HashSet<>();

    public BQMob(int id, String kind, int x, int y) {
        super(id, "mob", kind, x, y);
    }

    @Override
    public void onObjectAddedToKnownList(BQObject BQCharacter) {
        if (BQCharacter instanceof BQPlayer) {
            JsonArray chatPacket = new JsonArray();
            chatPacket.addNumber(Packet.CHAT);
            chatPacket.addNumber(getId());
            chatPacket.addString("Arghhh!");

            ((BQPlayer) BQCharacter).getConnection().write(chatPacket.encode());
        }
    }

    @Override
    public void onObjectRemovedFromKnownList(BQObject BQCharacter) {
        super.onObjectRemovedFromKnownList(BQCharacter);
    }
}
