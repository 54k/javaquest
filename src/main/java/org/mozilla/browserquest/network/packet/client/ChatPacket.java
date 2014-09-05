package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class ChatPacket extends Packet {

    private String message;

    @Override
    public void setData(Object[] data) {
        message = (String) data[0];
    }

    @Override
    public void run() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(getConnection().getPlayer().getId());
        jsonArray.add(message);
        getConnection().write(jsonArray.encode());
    }
}
