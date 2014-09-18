package org.mozilla.browserquest.network;

import org.mozilla.browserquest.model.actor.BQPlayer;

public interface NetworkConnection {

    BQPlayer getPlayer();

    void setPlayer(BQPlayer player);

    void close();

    void write(String text);
}
