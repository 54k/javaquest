package org.mozilla.browserquest.network;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.service.WorldService;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.PacketHandler;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.mozilla.browserquest.service.ObjectFactory;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

public class NetworkConnectionImpl implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;

    @LazyInject
    private Vertx vertx;
    @LazyInject
    private WorldService worldService;
    @LazyInject
    private ObjectFactory objectFactory;

    private PacketHandler packetHandler;
    private ServerWebSocket channel;
    private PlayerObject player;

    private long disconnectTaskId;

    public NetworkConnectionImpl(ServerWebSocket channel) {
        packetHandler = new PacketHandler();
        this.channel = channel;
        this.channel.frameHandler(this::onFrame);
        this.channel.closeHandler(this::onDisconnect);
        this.channel.writeTextFrame("go");
    }

    @Override
    public PlayerObject getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(PlayerObject player) {
        this.player = player;
    }

    private void onFrame(WebSocketFrame frame) {
        if (frame.isText()) {
            String textData = frame.textData();
            Object[] packet = Json.decodeValue(textData, Object[].class);
            packetHandler.handle(this, packet);
            resetDisconnectTimeout();
        }
    }

    private void onDisconnect(Void v) {
        vertx.cancelTimer(disconnectTaskId);
        player.getPositionController().decay();
        worldService.removeObject(player);
        objectFactory.destroyObject(player);
    }

    private void resetDisconnectTimeout() {
        vertx.cancelTimer(disconnectTaskId);
        disconnectTaskId = vertx.setTimer(DISCONNECT_TIMEOUT, e -> channel.close());
    }

    @Override
    public void write(String text) {
        channel.writeTextFrame(text);
    }

    @Override
    public void write(ServerPacket packet) {
        channel.writeTextFrame(packet.write());
    }

    @Override
    public void close() {
        channel.close();
    }
}
