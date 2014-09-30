package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.WorldMapInstance;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.gameserver.service.WorldService;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.util.PositionUtil;
import org.mozilla.browserquest.util.RandomUtils;
import org.vertx.java.core.json.JsonArray;

import java.util.Collection;

public class EnterWorld extends ClientPacket {

    @LazyInject
    private WorldService worldService;
    @LazyInject
    private ObjectFactory objectFactory;

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
            getConnection().setPlayer(objectFactory.createObject(PlayerObject.class));
        }

        PlayerObject player = getConnection().getPlayer();
        player.setConnection(getConnection());

        player.setName(playerName);

        WorldMapInstance availableWorldMapInstance = worldService.getAvailableWorldMapInstance();
        Collection<Area> startingAreas = availableWorldMapInstance.getWorldMap().getPlayerStartingAreas().values();
        int r = RandomUtils.getRandomBetween(0, startingAreas.size() - 1);
        Area area = startingAreas.stream().skip(r).findFirst().get();
        Position startPos = PositionUtil.getRandomPositionInside(area);
        PositionController positionController = player.getPositionController();
        positionController.setPosition(startPos.getX(), startPos.getY());
        positionController.setOrientation(Orientation.BOTTOM);

        JsonArray welcomePacket = new JsonArray(
                new Object[]{ClientPacket.WELCOME, player.getId(), player.getName(), startPos.getX(), startPos.getY(), player.getStatsController().getMaxHitPoints()});

        player.getStatusController().setHitPoints(player.getStatsController().getMaxHitPoints());

        getConnection().write(welcomePacket.encode());
        worldService.addObject(player);
        positionController.setWorldMapInstance(availableWorldMapInstance);

        positionController.spawn();
    }
}
