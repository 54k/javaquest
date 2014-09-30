package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class PopulationPacket extends ServerPacket {

    private int instanceCount;
    private int totalCount;

    public PopulationPacket(int instanceCount, int totalCount) {
        this.instanceCount = instanceCount;
        this.totalCount = totalCount;
    }

    @Override
    public String write() {
        return new JsonArray(new Object[]{ClientPacket.POPULATION, instanceCount, totalCount}).encode();
    }
}
