package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.interfaces.Movable;
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
        BQPlayer player = getConnection().getPlayer();
        player.asBehavior(Movable.class).moveTo(x, y);
    }
}
