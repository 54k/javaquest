package org.mozilla.browserquest.network.packet;

import org.mozilla.browserquest.Player;
import org.mozilla.browserquest.network.Command;
import org.mozilla.browserquest.network.Packet;
import org.vertx.java.core.json.JsonArray;

public class MovePacket extends Packet {

    @Override
    public void run() {
        int x = (int) getData()[1];
        int y = (int) getData()[2];
        Player player = getConnection().getPlayer();
        // TODO validate position
        player.setPosition(x, y);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Command.MOVE);
        jsonArray.addNumber(player.getId());   //id
        jsonArray.addNumber(player.getX());   //x
        jsonArray.addNumber(player.getY());      //y

        getConnection().write(jsonArray.toString());
    }
}
