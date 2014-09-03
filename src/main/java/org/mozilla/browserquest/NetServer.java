package org.mozilla.browserquest;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;

public class NetServer {

    private HttpServer server;
    private RouteMatcher routeMatcher;

    public NetServer(Vertx vertx) {
        server = vertx.createHttpServer();
        routeMatcher = new RouteMatcher();
        server.requestHandler(routeMatcher);
    }

    public NetServer listen(int port) {
        server.listen(port);
        return this;
    }

    public NetServer close() {
        server.close();
        return this;
    }

    public NetServer onWebSocketConnection(Handler<ServerWebSocket> connectHandler) {
        server.websocketHandler(connectHandler);
        return this;
    }

    public NetServer get(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.get(pattern, handler);
        return this;
    }

    public NetServer getWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.getWithRegEx(regex, handler);
        return this;
    }

    public NetServer noMatch(Handler<HttpServerRequest> handler) {
        routeMatcher.noMatch(handler);
        return this;
    }
}
