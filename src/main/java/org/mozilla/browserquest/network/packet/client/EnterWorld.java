package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.position.PositionController;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.template.CheckpointTemplate;
import org.mozilla.browserquest.template.WorldTemplate;
import org.vertx.java.core.json.JsonArray;

import java.util.concurrent.atomic.AtomicInteger;

public class EnterWorld extends Packet {

    private static final AtomicInteger seq = new AtomicInteger(0);

    @LazyInject
    private BQWorld world;
    @LazyInject
    private ActorFactory actorFactory;
    @LazyInject
    private WorldTemplate template;
    @LazyInject
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

        PositionController positionController = player.getPositionController();
        positionController.setPosition(startPosition.getX(), startPosition.getY());
        positionController.setOrientation(Orientation.BOTTOM);

        JsonArray welcomePacket = new JsonArray(new Object[]{Packet.WELCOME, player.getId(), player.getName(), startPosition.getX(), startPosition.getY(), player.getStatsController().getMaxHitPoints()});

        getConnection().write(welcomePacket.encode());

        world.addObject(player);
        positionController.setWorld(world);

        positionController.spawn();
    }
}
