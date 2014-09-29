package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class MovePacket extends ServerPacket {

    private int actor;
    private int x;
    private int y;

    public MovePacket(int actor, int x, int y) {
        this.actor = actor;
        this.x = x;
        this.y = y;
    }

    @Override
    public String write() {
        JsonArray movePacket = new JsonArray(new Object[]{ClientPacket.MOVE, actor, x, y});
        return movePacket.encode();
    }
}
