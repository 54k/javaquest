package org.mozilla.browserquest.net;

import org.mozilla.browserquest.network.packet.ServerPacket;
import org.mozilla.browserquest.space.IAppSpace;
import org.vertx.java.core.json.impl.Json;

public class NetworkClient {

    private IAppSpace space;
    private NettyConnection connection;
    private IPacketHandler packetHandler;

    public NetworkClient(IAppSpace space, IPacketHandler packetHandler, NettyConnection connection) {
        this.space = space;
        this.connection = connection;
        this.packetHandler = packetHandler;
        init();
    }

    private void init() {
        connection.onMessage(this::handlePacket);
        connection.write("go");
    }

    private void handlePacket(String textData) {
        Object[] packet = Json.decodeValue(textData, Object[].class);
        invokeLater(() -> packetHandler.handle(NetworkClient.this, packet));
    }

    private void invokeLater(Runnable runnable) {
        space.getExecutor().execute(runnable);
    }

    public void write(ServerPacket serverPacket) {
        connection.write(serverPacket.write());
    }

    public void close() {
        connection.close();
    }
}
