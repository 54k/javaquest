package org.mozilla.browserquest;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.http.ServerWebSocket;

public class WebSocketServer {

    private HttpServer httpServer;
    private Handler<HttpServerResponse> statusHandler;

    public WebSocketServer(Vertx vertx) {
        httpServer = vertx.createHttpServer();
    }

    public void listen(int port) {
        httpServer.listen(port);
    }

    public void close() {
        httpServer.close();
    }

    public void onConnect(Handler<ServerWebSocket> connectHandler) {
        httpServer.websocketHandler(connectHandler);
    }

    public void onStatusRequest(Handler<HttpServerResponse> statusHandler) {
        httpServer.requestHandler(req -> {
            if (req.path().equalsIgnoreCase("/status")) {
                req.response().setStatusCode(200);
                statusHandler.handle(req.response());
            } else {
                req.response().setStatusCode(404).end();
            }
        });
    }
}
