package org.mozilla.browserquest.network;

import com.google.inject.Inject;
import org.mozilla.browserquest.Player;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

public class WebSocketNetworkConnection implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;

    @Inject
    private Vertx vertx;
    @Inject
    private PacketProcessor packetProcessor;
    private ServerWebSocket delegate;

    private Player player;

    private long disconnectTimeoutId;

    public WebSocketNetworkConnection(ServerWebSocket delegate) {
        this.delegate = delegate;
        this.delegate.frameHandler(this::onFrame);
        this.delegate.closeHandler(v -> vertx.cancelTimer(disconnectTimeoutId));
    }

    private void onFrame(WebSocketFrame frame) {
        if (frame.isText()) {
            String textData = frame.textData();
            Object[] packet = Json.decodeValue(textData, Object[].class);
            packetProcessor.process(this, packet);
            resetDisconnectTimeout();
        }
    }

    private void resetDisconnectTimeout() {
        vertx.cancelTimer(disconnectTimeoutId);
        disconnectTimeoutId = vertx.setTimer(DISCONNECT_TIMEOUT, e -> delegate.close());
    }
}
