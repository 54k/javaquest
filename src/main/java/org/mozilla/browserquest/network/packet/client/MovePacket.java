package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.Broadcast;
import org.vertx.java.core.json.JsonArray;

public class MovePacket extends Packet {

    @Inject
    private BQWorld world;

    private int x;
    private int y;

    @Override
    public void setData(Object[] data) {
        x = (int) data[0];
        y = (int) data[1];
    }

    @Override
    public void run() {
        BQPlayer player = getConnection().getPlayer();
        //
        //        if (!BQPlayer.getWorld().isValidPosition(x, y)) {
        //            getConnection().close();
        //        }
        //
        //        BQPlayer.setPosition(new Position(x, y));
        //        BQPlayer.getWorld().updateCharacterRegionAndKnownList(BQPlayer);
        player.setXY(x, y);
        world.updateObject(player);

        JsonArray jsonArray = new JsonArray();
        jsonArray.addNumber(Packet.MOVE);
        jsonArray.addNumber(player.getId());   //id
        Position position = player.getPosition();
        jsonArray.addNumber(position.getX());   //x
        jsonArray.addNumber(position.getY());      //y

        Broadcast.toSelfAndKnownPlayers(player, jsonArray.toString());
    }
}
