package org.mozilla.browserquest.network.packet;

import org.mozilla.browserquest.Player;
import org.mozilla.browserquest.network.Command;
import org.mozilla.browserquest.network.Packet;
import org.vertx.java.core.json.JsonArray;

public class HelloPacket extends Packet {

    @Override
    public void run() {
        if (getConnection().getPlayer() == null) {
            getConnection().setPlayer(new Player());
        }
        Player player = getConnection().getPlayer();

        boolean hasEnteredInGame = player.isHasEnteredInGame();

        if (hasEnteredInGame) {
            // HELLO packet should be sent only once
            getConnection().close();
        }
        Object[] packet = getData();
        String name = (String) packet[1];

        player.setId(1);
        player.setPosition(22, 61);
        player.setName(name);
        player.setHasEnteredInGame(true);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Command.WELCOME);
        jsonArray.addNumber(1);   //id
        jsonArray.addString(name);   //name
        jsonArray.addNumber(22);   //x
        jsonArray.addNumber(61);      //y
        jsonArray.addNumber(50);        //hp

        getConnection().write(jsonArray.toString());
    }
}
