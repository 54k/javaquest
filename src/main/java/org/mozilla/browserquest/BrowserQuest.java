package org.mozilla.browserquest;

import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BrowserQuest extends Verticle {

    private NetServer netServer;
    private Set<WorldServer> worlds = new HashSet<>();

    @Override
    public void start() {
        getContainer().logger().info("Starting BrowserQuest server");
        JsonObject config = getContainer().config();
        int port = Optional.ofNullable(config.getInteger("serverPort")).orElse(8000);
        int worldCount = Optional.ofNullable(config.getInteger("worldCount")).orElse(10);
        int maxPlayers = Optional.ofNullable(config.getInteger("maxPlayersPerWorld")).orElse(100);

        populateWorlds(worldCount, maxPlayers);

        netServer = new NetServer(getVertx()).noMatch(this::onNotFoundRequest).getWithRegEx("^/client/.+", this::onContentRequest).get("/status", this::onStatusRequest)
                .onWebSocketConnection(this::onWebSocketConnection).listen(port);
        getContainer().logger().info("BrowserQuest started at port " + port);
    }

    @Override
    public void stop() {
        netServer.close();
    }

    private void populateWorlds(int worldCount, int maxPlayers) {
        for (int i = 0; i < worldCount; i++) {
            WorldServer worldServer = new WorldServer("world-" + (i + 1), maxPlayers, netServer);
            worldServer.run(new Map());
            worlds.add(worldServer);
        }
    }

    private void onNotFoundRequest(HttpServerRequest request) {
        request.response().setStatusCode(404).end();
    }

    private void onContentRequest(HttpServerRequest request) {
        FileSystem fileSystem = getVertx().fileSystem();
        String path = request.path().substring(1);
        fileSystem.exists(path, exists -> {
            if (exists.result()) {
                request.response().sendFile(path);
            } else {
                request.response().setStatusCode(404).end();
            }
        });
    }

    private void onStatusRequest(HttpServerRequest request) {
        request.response().setStatusCode(200).end(getWorldDistribution());
    }

    private String getWorldDistribution() {
        JsonArray status = new JsonArray();
        worlds.stream().forEach(world -> status.add(world.getPlayersCount()));
        return status.encode();
    }

    private void onWebSocketConnection(ServerWebSocket webSocket) {
        WorldServer worldServer = getAvailableWorldServer();
        if (worldServer == null) {
            webSocket.close();
        } else {
            worldServer.addPlayer(new Player(webSocket, worldServer));
        }
    }

    private WorldServer getAvailableWorldServer() {
        return worlds.stream().filter(world -> world.getPlayersCount() < world.getMaxPlayers()).findFirst().get();
    }
}
