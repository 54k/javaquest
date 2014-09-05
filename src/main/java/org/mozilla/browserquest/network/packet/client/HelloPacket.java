package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.Player;
import org.mozilla.browserquest.network.Command;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class HelloPacket extends Packet {

    private String playerName;
    private int x;
    private int y;

    @Override
    public void setData(Object[] data) {
        playerName = (String) data[0];
        x = (int) data[1];
        y = (int) data[2];
    }

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

        player.setId(1);
        player.setPosition(x, y);
        player.setName(playerName);
        player.setHasEnteredInGame(true);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Command.WELCOME);
        jsonArray.addNumber(1);   //id
        jsonArray.addString(playerName);   //name
        jsonArray.addNumber(x);   //x
        jsonArray.addNumber(y);      //y
        jsonArray.addNumber(50);        //hp

        getConnection().write(jsonArray.toString());
    }
}
