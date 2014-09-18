package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.idfactory.IdFactory;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.template.BQWorldTemplate;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.vertx.java.core.json.JsonArray;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloPacket extends Packet {

    private static final AtomicInteger seq = new AtomicInteger(0);

    @Inject
    private BQWorld world;
    @Inject
    private ActorFactory actorFactory;
    @Inject
    private BQWorldTemplate template;
    @Inject
    private IdFactory idFactory;

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

        BQPlayer player = getConnection().getPlayer();
        player.setConnection(getConnection());

        player.setId(idFactory.getNextId());
        player.setName(playerName);

        Position startPosition = new Position();
        for (CheckpointTemplate checkpointTemplate : template.getCheckpoints()) {
            if (checkpointTemplate.getS() == 1) {
                startPosition.setX(checkpointTemplate.getX());
                startPosition.setY(checkpointTemplate.getY());
                break;
            }
        }

        player.setPosition(startPosition);

        JsonArray welcomePacket = new JsonArray();
        welcomePacket.addNumber(Packet.WELCOME);
        welcomePacket.addNumber(player.getId());   //id
        welcomePacket.addString(player.getName());   //name
        welcomePacket.addNumber(player.getX());   //x
        welcomePacket.addNumber(player.getY());      //y
        welcomePacket.addNumber(0);        //hp

        getConnection().write(welcomePacket.encode());

        world.addObject(player);
        world.spawnObject(player);
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
