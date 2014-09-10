package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.World;
import org.mozilla.browserquest.WorldInstance;
import org.mozilla.browserquest.model.Player;
import org.mozilla.browserquest.network.packet.Packet;
import org.vertx.java.core.json.JsonArray;

public class HelloPacket extends Packet {

    @Inject
    private World world;

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

        WorldInstance worldInstance = world.getAvailableWorldInstance();
        if (worldInstance == null) {
            getConnection().close();
            return;
        }

        Position position;
        if (worldInstance.isValidPosition(x, y)) {
            position = new Position(x, y);
        } else {
            position = worldInstance.getRandomStartingPosition();
        }
        worldInstance.addPlayer(player);

        player.setId(1);
        player.setPosition(position);
        player.setName(playerName);
        player.setHasEnteredInGame(true);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.WELCOME);
        jsonArray.addNumber(player.getId());   //id
        jsonArray.addString(player.getName());   //name
        jsonArray.addNumber(player.getX());   //x
        jsonArray.addNumber(player.getY());      //y
        jsonArray.addNumber(player.getHitPoints());        //hp

        getConnection().write(jsonArray.toString());
    }
}
