package org.mozilla.browserquest.network;

import org.mozilla.browserquest.model.BQPlayer;

public interface NetworkConnection {

    BQPlayer getBQPlayer();

    void setBQPlayer(BQPlayer BQPlayer);

    void close();

    void write(String text);
}
