package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.model.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.Broadcast;
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
        BQPlayer BQPlayer = getConnection().getBQPlayer();

        if (!BQPlayer.getWorldInstance().isValidPosition(x, y)) {
            getConnection().close();
        }

        BQPlayer.setPosition(new Position(x, y));
        BQPlayer.getWorldInstance().updateCharacterRegionAndKnownList(BQPlayer);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.MOVE);
        jsonArray.addNumber(BQPlayer.getId());   //id
        jsonArray.addNumber(BQPlayer.getX());   //x
        jsonArray.addNumber(BQPlayer.getY());      //y

        Broadcast.toSelfAndKnownPlayers(BQPlayer, jsonArray.toString());
    }
}
