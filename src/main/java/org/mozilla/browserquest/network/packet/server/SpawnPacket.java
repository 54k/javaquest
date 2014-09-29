package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class SpawnPacket extends ServerPacket {

    private BaseObject object;

    public SpawnPacket(BaseObject object) {
        this.object = object;
    }

    @Override
    public String write() {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(ClientPacket.SPAWN);
        object.getInfo().forEach(spawnPacket::add);
        return spawnPacket.encode();
    }
}
