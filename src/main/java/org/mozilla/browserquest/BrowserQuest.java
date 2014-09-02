package org.mozilla.browserquest;

import org.vertx.java.core.http.HttpServer;
import org.vertx.java.platform.Verticle;

import java.util.Optional;

public class BrowserQuest extends Verticle {

    HttpServer httpServer;

    @Override
    public void start() {
        int port = Optional.ofNullable(getContainer().config().getInteger("port")).orElse(3000);

        httpServer = getVertx().createHttpServer();
        httpServer.websocketHandler(ws -> ws.frameHandler(frame -> ws.writeTextFrame(frame.textData())));
        httpServer.listen(port);
        getContainer().logger().info("BrowserQuest started at port " + port);
    }

    @Override
    public void stop() {
        httpServer.close();
    }
}
