package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.model.Player;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.world.World;
import org.mozilla.browserquest.world.WorldInstance;
import org.vertx.java.core.json.JsonArray;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloPacket extends Packet {

    private static final AtomicInteger seq = new AtomicInteger(0);

    @Inject
    private World world;

    private String playerName;
    private int armor;
    private int weapon;

    @Override
    public void setData(Object[] data) {
        playerName = (String) data[0];
        armor = (int) data[1];
        weapon = (int) data[2];
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

        player.setConnection(getConnection());

        Position position = worldInstance.getRandomStartingPosition();

        player.setId(seq.incrementAndGet());
        player.setPosition(position);
        player.setName(playerName);

        if (worldInstance.addPlayer(player)) {
            player.setHasEnteredInGame(true);

            JsonArray welcomePacket = new JsonArray();
            welcomePacket.addNumber(Packet.WELCOME);
            welcomePacket.addNumber(player.getId());   //id
            welcomePacket.addString(player.getName());   //name
            welcomePacket.addNumber(player.getX());   //x
            welcomePacket.addNumber(player.getY());      //y
            welcomePacket.addNumber(player.getHitPoints());        //hp

            getConnection().write(welcomePacket.encode());

            worldInstance.updatePlayerRegionAndKnownList(player);

            world.broadcastWorldPopulation();
        }
    }
}
