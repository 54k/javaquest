package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.network.packet.Packet;

public class StartMove extends Packet {

    private int x;
    private int y;

    @Override
    public void setData(Object[] data) {
        x = (int) data[0];
        y = (int) data[1];
    }

    @Override
    public void run() {
        PlayerObject player = getConnection().getPlayer();
        player.getMovementController().moveTo(x, y);
    }
}
