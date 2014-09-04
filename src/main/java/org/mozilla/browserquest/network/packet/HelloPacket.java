package org.mozilla.browserquest.network.packet;

import org.mozilla.browserquest.Player;
import org.mozilla.browserquest.network.Command;
import org.mozilla.browserquest.network.Packet;
import org.vertx.java.core.json.JsonArray;

public class HelloPacket extends Packet {

    @Override
    public void run() {
        getConnection().setPlayer(new Player());
        boolean hasEnteredInGame = getConnection().getPlayer().isHasEnteredInGame();

        if (hasEnteredInGame) {
            getConnection().close();
        }
        Object[] packet = getData();
        String name = (String) packet[1];

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Command.WELCOME);
        jsonArray.addNumber(1);   //id
        jsonArray.addString(name);   //name
        jsonArray.addNumber(0);   //x
        jsonArray.addNumber(0);      //y
        jsonArray.addNumber(50);        //hp

        getConnection().write(jsonArray.toString());

        getConnection().getPlayer().setHasEnteredInGame(true);
    }
}
