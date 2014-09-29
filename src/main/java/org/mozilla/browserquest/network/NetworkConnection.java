package org.mozilla.browserquest.network;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

public interface NetworkConnection {

    PlayerObject getPlayer();

    void setPlayer(PlayerObject player);

    void close();

    void write(String text);
}
