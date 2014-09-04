package org.mozilla.browserquest.network;

import org.mozilla.browserquest.Player;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

public class WebSocketNetworkConnection implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;

    private Vertx vertx;
    private PacketHandler packetHandler;
    private ServerWebSocket channel;

    private Player player;

    private long disconnectTaskId;

    public WebSocketNetworkConnection(Vertx vertx, ServerWebSocket channel) {
        this.vertx = vertx;
        packetHandler = new PacketHandler();
        this.channel = channel;
        this.channel.frameHandler(this::onFrame);
        this.channel.closeHandler(v -> vertx.cancelTimer(disconnectTaskId));
        this.channel.writeTextFrame("go");
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
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
