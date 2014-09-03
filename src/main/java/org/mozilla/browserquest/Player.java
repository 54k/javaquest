package org.mozilla.browserquest;

import org.vertx.java.core.http.ServerWebSocket;

public class Player {

    private ServerWebSocket connection;
    private WorldServer worldServer;

    public Player(ServerWebSocket connection, WorldServer worldServer) {
        this.connection = connection;
        this.worldServer = worldServer;
        this.connection.frameHandler(f -> {
            if (f.isText()) {
                onFrameData(f.textData());
            }
        });
    }

    private void onFrameData(String textData) {

    }

    private void write(String textData) {
        connection.writeTextFrame(textData);
    }
}
