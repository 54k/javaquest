package org.mozilla.browserquest.network;

import org.mozilla.browserquest.Player;

public interface NetworkConnection {

    Player getPlayer();

    void setPlayer(Player player);

    void close();

    void write(String text);
}
