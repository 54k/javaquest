package org.mozilla.browserquest;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.mozilla.browserquest.gameserver.service.SpawnService;
import org.mozilla.browserquest.gameserver.service.WorldService;
import org.mozilla.browserquest.gameserver.service.WorldServiceImpl;
import org.mozilla.browserquest.network.NetworkServer;
import org.mozilla.browserquest.network.NetworkServerImpl;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import java.util.Optional;

public class BrowserQuest extends Verticle {

    @Inject
    private Logger logger;
    @Inject
    private FileSystem fileSystem;

    private WorldService worldService;
    private NetworkServer networkServer;

    @Override
    public void start() {
        Injector injector = Guice.createInjector(new BrowserQuestModule(getVertx(), getContainer()));
        injector.injectMembers(this);

        logger.info("Starting BrowserQuest server");

        JsonObject config = getContainer().config();
        int port = Optional.ofNullable(config.getInteger("serverPort")).orElse(8000);

        worldService = injector.getInstance(WorldService.class);
        worldService.createWorldMapInstance(1);

        networkServer = new NetworkServerImpl();
        networkServer.noMatch(this::onNotFoundRequest).getWithRegEx("^/client/.+", this::onContentRequest).get("/status", this::onStatusRequest).listen(port);

        logger.info("BrowserQuest started at port " + port);
    }

    @Override
    public void stop() {
        networkServer.close();
    }

    private void onNotFoundRequest(HttpServerRequest request) {
        request.response().setStatusCode(404).end();
    }

    private void onContentRequest(HttpServerRequest request) {
        String path = request.path().substring(1);
        fileSystem.exists(path, exists -> {
            if (exists.result()) {
                request.response().sendFile(path);
            } else {
                onNotFoundRequest(request);
            }
        });
    }

    private void onStatusRequest(HttpServerRequest request) {
        request.response().setStatusCode(200).end(getWorldDistribution());
    }

    private String getWorldDistribution() {
        JsonArray status = new JsonArray(new Object[]{worldService.getPlayers().size()});
        return status.encode();
    }
}
