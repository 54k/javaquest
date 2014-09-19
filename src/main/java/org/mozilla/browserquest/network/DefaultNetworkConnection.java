package org.mozilla.browserquest.network;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.mozilla.browserquest.idfactory.IdFactory;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.PacketHandler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.impl.Json;

//import org.mozilla.browserquest.world.WorldInstance;

public class DefaultNetworkConnection implements NetworkConnection {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;
    private final Injector injector;

    private Vertx vertx;
    private PacketHandler packetHandler;
    private ServerWebSocket channel;

    private BQPlayer player;

    private long disconnectTaskId;

    @Inject
    private BQWorld world;
    @Inject
    private IdFactory idFactory;

    public DefaultNetworkConnection(Vertx vertx, Injector injector, ServerWebSocket channel) {
        this.vertx = vertx;
        this.injector = injector;
        injector.injectMembers(this);
        packetHandler = new PacketHandler(this.injector);
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
        player.decayMe();
        world.removeObject(player);
        idFactory.releaseId(player.getId());
        //        WorldInstance worldInstance = BQPlayer.getWorld();
        //        if (worldInstance != null) {
        //            BQPlayer.getKnownList().clearKnownObjects();
        //            worldInstance.removePlayer(BQPlayer);
        //            worldInstance.getWorld().broadcastWorldPopulation();
        //        }
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
