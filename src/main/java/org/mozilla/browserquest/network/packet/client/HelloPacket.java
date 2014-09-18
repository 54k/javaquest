package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloPacket extends Packet {

    private static final AtomicInteger seq = new AtomicInteger(0);

    @Inject
    private BQWorld world;
    @Inject
    private ActorFactory actorFactory;

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
            getConnection().setPlayer(actorFactory.newActor(BQPlayer.class));
        }

        BQPlayer BQPlayer = getConnection().getPlayer();
        //        boolean hasEnteredInGame = BQPlayer.isHasEnteredInGame();
        //
        //        if (hasEnteredInGame) {
        //            // HELLO packet should be sent only once
        //            getConnection().close();
        //        }
        //
        //        WorldInstance world = this.world.getAvailableWorldInstance();
        //        if (world == null) {
        //            getConnection().close();
        //            return;
        //        }
        //
        //        BQPlayer.setConnection(getConnection());
        //
        //        Position position = world.getRandomStartingPosition();
        //
        //        BQPlayer.setId(seq.incrementAndGet());
        //        BQPlayer.setPosition(position);
        //        BQPlayer.setName(playerName);
        //
        //        if (world.addPlayer(BQPlayer)) {
        //            BQPlayer.setHasEnteredInGame(true);
        //
        //            JsonArray welcomePacket = new JsonArray();
        //            welcomePacket.addNumber(Packet.WELCOME);
        //            welcomePacket.addNumber(BQPlayer.getId());   //id
        //            welcomePacket.addString(BQPlayer.getName());   //name
        //            welcomePacket.addNumber(BQPlayer.getX());   //x
        //            welcomePacket.addNumber(BQPlayer.getY());      //y
        //            welcomePacket.addNumber(BQPlayer.getHitPoints());        //hp
        //
        //            getConnection().write(welcomePacket.encode());
        //
        //            world.updateCharacterRegionAndKnownList(BQPlayer);
        //
        //            this.world.broadcastWorldPopulation();
        //        }
    }
}
