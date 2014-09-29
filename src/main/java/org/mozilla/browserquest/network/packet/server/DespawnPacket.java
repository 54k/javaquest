package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class DespawnPacket extends ServerPacket {

    private int object;

    public DespawnPacket(int object) {
        this.object = object;
    }

    @Override
    public String write() {
        JsonArray spawnPacket = new JsonArray(new Object[]{ClientPacket.DESPAWN, object});
        return spawnPacket.encode();
    }
}
