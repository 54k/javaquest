package org.mozilla.browserquest.network;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.network.packet.ServerPacket;

public interface NetworkConnection {

    PlayerObject getPlayer();

    void setPlayer(PlayerObject player);

    void close();

    void write(String text);

    void write(ServerPacket packet);
}
