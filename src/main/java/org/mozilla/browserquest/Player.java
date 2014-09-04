package org.mozilla.browserquest;

import com.google.inject.Inject;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.WebSocketFrame;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.impl.Json;

import java.util.Optional;

public class Player {

    private static final long DISCONNECT_TIMEOUT = 1000 * 60 * 15;
    @Inject
    private Vertx vertx;

    private ServerWebSocket connection;
    private WorldServer worldServer;

    private long disconnectTimeoutId;

    private boolean hasEnteredInGame;
    private String name;

    public Player(Vertx vertx, ServerWebSocket connection, WorldServer worldServer) {
        this.vertx = vertx;
        this.connection = connection;
        this.worldServer = worldServer;
        this.connection.frameHandler(this::onFrame);
        this.connection.closeHandler(v -> vertx.cancelTimer(disconnectTimeoutId));
        resetDisconnectTimeout();
        connection.writeTextFrame("go");
    }

    private void onFrame(WebSocketFrame frame) {
        if (frame.isText()) {
            String textData = frame.textData();
            Object[] packet = Json.decodeValue(textData, Object[].class);
            int opcode = (int) packet[0];
            onCommand(opcode, packet);
        }
    }

    private void onCommand(int opcode, Object[] packet) {
        if (!hasEnteredInGame && opcode != Command.HELLO) {
            connection.close();
        }

        if (hasEnteredInGame && opcode == Command.HELLO) {
            connection.close();
        }

        if (opcode == Command.HELLO) {
            String name = (String) packet[1];
            this.name = Optional.ofNullable(name).orElse("lorem ipsum");
            worldServer.addPlayer(this);

            JsonArray jsonArray = new JsonArray();
            jsonArray.addNumber(Command.WELCOME);
            jsonArray.addNumber(1);
            jsonArray.addString(this.name);
            jsonArray.addNumber(0);
            jsonArray.addNumber(0);
            jsonArray.addNumber(50);

            hasEnteredInGame = true;
            write(jsonArray.toString());
        }
    }

    private void resetDisconnectTimeout() {
        vertx.cancelTimer(disconnectTimeoutId);
        disconnectTimeoutId = vertx.setTimer(DISCONNECT_TIMEOUT, e -> connection.close());
    }

    private void write(String textData) {
        connection.writeTextFrame(textData);
    }
}
