package org.mozilla.browserquest.net;

public interface IPacketHandler {

    void handle(NetworkClient client, Object[] packet);
}
