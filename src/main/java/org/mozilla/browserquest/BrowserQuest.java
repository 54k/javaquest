package org.mozilla.browserquest;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.network.DefaultNetworkServer;
import org.mozilla.browserquest.network.NetworkServer;
import org.mozilla.browserquest.script.ScriptService;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import javax.script.SimpleScriptContext;
import java.io.File;
import java.util.Optional;

public class BrowserQuest extends Verticle {

    @Inject
    private Logger logger;
    @Inject
    private FileSystem fileSystem;
    @Inject
    private BQWorld world;
    @Inject
    private ScriptService scriptService;

    private NetworkServer networkServer;

    @Override
    public void start() {
        Injector injector = Guice.createInjector(new BrowserQuestModule(getVertx(), getContainer()));
        injector.injectMembers(this);
        SimpleScriptContext ctx = new SimpleScriptContext();
        scriptService.newProxy(Runnable.class, new File(ScriptService.SCRIPT_FOLDER, "test.js"), ctx).run();

        logger.info("Starting BrowserQuest server");

        JsonObject config = getContainer().config();
        int port = Optional.ofNullable(config.getInteger("serverPort")).orElse(8000);

        networkServer = new DefaultNetworkServer(vertx, injector);
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
        JsonArray status = new JsonArray();
        //        world.getWorldInstances().stream().forEach(world -> status.add(world.getPlayersCount()));
        return status.encode();
    }
}
