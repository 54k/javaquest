package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.Player;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class MovePacket extends Packet {

    private int x;
    private int y;

    @Override
    public void setData(Object[] data) {
        x = (int) data[0];
        y = (int) data[1];
    }

    @Override
    public void run() {
        Player player = getConnection().getPlayer();
        // TODO validate position
        player.setPosition(x, y);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.MOVE);
        jsonArray.addNumber(player.getId());   //id
        jsonArray.addNumber(player.getX());   //x
        jsonArray.addNumber(player.getY());      //y

        getConnection().write(jsonArray.toString());
    }
}
