package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.network.NetworkConnection;
import org.mozilla.browserquest.network.packet.PacketHandler;

public class SpaceClient {

    private Actor pawn;

    private PacketHandler packetHandler = new PacketHandler();
    private NetworkConnection networkConnection;

    public SpaceClient(NetworkConnection networkConnection) {
        this.networkConnection = networkConnection;
    }
}
