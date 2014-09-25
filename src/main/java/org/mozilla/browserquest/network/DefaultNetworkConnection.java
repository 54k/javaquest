package org.mozilla.browserquest.network;

import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.PacketHandler;
import org.mozilla.browserquest.service.IdFactory;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

public class DefaultNetworkConnection implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;

    @LazyInject
    private Vertx vertx;
    @LazyInject
    private BQWorld world;
    @LazyInject
    private IdFactory idFactory;

    private PacketHandler packetHandler;
    private ServerWebSocket channel;
    private BQPlayer player;

    private long disconnectTaskId;

    public DefaultNetworkConnection(ServerWebSocket channel) {
        packetHandler = new PacketHandler();
        this.channel = channel;
        this.channel.frameHandler(this::onFrame);
        this.channel.closeHandler(this::onDisconnect);
        this.channel.writeTextFrame("go");
    }

    @Override
    public BQPlayer getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(BQPlayer player) {
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
        world.removeObject(player);
        idFactory.releaseId(player.getId());
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
    public void close() {
        channel.close();
    }
}
