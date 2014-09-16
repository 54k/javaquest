package org.mozilla.browserquest.network;

import com.google.inject.Injector;
import org.mozilla.browserquest.model.BQPlayer;
import org.mozilla.browserquest.network.packet.PacketHandler;
import org.mozilla.browserquest.world.WorldInstance;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

public class DefaultNetworkConnection implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;

    private Vertx vertx;
    private PacketHandler packetHandler;
    private ServerWebSocket channel;

    private BQPlayer BQPlayer;

    private long disconnectTaskId;

    public DefaultNetworkConnection(Vertx vertx, Injector injector, ServerWebSocket channel) {
        this.vertx = vertx;
        packetHandler = new PacketHandler(injector);
        this.channel = channel;
        this.channel.frameHandler(this::onFrame);
        this.channel.closeHandler(this::onDisconnect);
        this.channel.writeTextFrame("go");
    }

    @Override
    public BQPlayer getBQPlayer() {
        return BQPlayer;
    }

    @Override
    public void setBQPlayer(BQPlayer BQPlayer) {
        this.BQPlayer = BQPlayer;
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
        WorldInstance worldInstance = BQPlayer.getWorldInstance();
        if (worldInstance != null) {
            BQPlayer.getKnownList().clearKnownObjects();
            worldInstance.removePlayer(BQPlayer);
            worldInstance.getWorld().broadcastWorldPopulation();
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
