package org.mozilla.browserquest;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.Arrays;
import java.util.Optional;

public class BrowserQuest extends Verticle {

    private WebSocketServer webSocketServer;
    private WorldServer[] worlds;

    @Override
    public void start() {
        getContainer().logger().info("Starting BrowserQuest server");

        JsonObject config = getContainer().config();

        int port = Optional.ofNullable(config.getInteger("serverPort")).orElse(3000);
        webSocketServer = new WebSocketServer(getVertx());
        webSocketServer.onStatusRequest(res -> res.end(getWorldDistribution()));
        webSocketServer.onConnect(ws -> {
            WorldServer worldServer = getAvailableWorldServer();
            worldServer.addPlayer(new Player());
        });

        int worldCount = Optional.ofNullable(config.getInteger("worldCount")).orElse(5);
        int maxPlayers = Optional.ofNullable(config.getInteger("maxPlayersPerWorld")).orElse(10);
        populateWorlds(worldCount, maxPlayers);

        webSocketServer.listen(port);
        getContainer().logger().info("BrowserQuest started at port " + port);
    }

    @Override
    public void stop() {
        webSocketServer.close();
    }

    private void populateWorlds(int worldCount, int maxPlayers) {
        worlds = new WorldServer[worldCount];
        for (int i = 0; i < worldCount; i++) {
            worlds[i] = new WorldServer("world-" + (i + 1), maxPlayers, webSocketServer);
        }
    }

    private WorldServer getAvailableWorldServer() {
        return Arrays.stream(worlds).filter(w -> w.getPlayersCount() < w.getMaxPlayers()).findFirst().get();
    }

    private String getWorldDistribution() {
        JsonArray status = new JsonArray();
        Arrays.stream(worlds).forEach(w -> status.add(w.getPlayersCount()));
        return status.encode();
    }
}
