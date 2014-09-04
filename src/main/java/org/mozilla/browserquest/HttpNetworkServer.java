package org.mozilla.browserquest;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;

public class HttpNetworkServer implements NetworkServer {

    private HttpServer server;
    private RouteMatcher routeMatcher;

    public HttpNetworkServer(Vertx vertx) {
        server = vertx.createHttpServer();
        routeMatcher = new RouteMatcher();
        setupServer();
    }

    private void setupServer() {
        server.requestHandler(routeMatcher);
        server.setCompressionSupported(true);
        server.setUsePooledBuffers(true);
    }

    @Override
    public NetworkServer listen(int port, String host) {
        server.listen(port, host);
        return this;
    }

    @Override
    public NetworkServer listen(int port) {
        server.listen(port);
        return this;
    }

    @Override
    public NetworkServer close() {
        server.close();
        return this;
    }

    @Override
    public NetworkServer close(Handler<AsyncResult<Void>> doneHandler) {
        server.close(doneHandler);
        return this;
    }

    @Override
    public NetworkServer listen(int port, String host, Handler<AsyncResult<HttpServer>> listenHandler) {
        server.listen(port, host, listenHandler);
        return this;
    }

    @Override
    public NetworkServer listen(int port, Handler<AsyncResult<HttpServer>> listenHandler) {
        server.listen(port, listenHandler);
        return this;
    }

    @Override
    public NetworkServer onWebSocketConnection(Handler<ServerWebSocket> connectHandler) {
        server.websocketHandler(connectHandler);
        return this;
    }

    @Override
    public NetworkServer get(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.get(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer handle(HttpServerRequest request) {
        routeMatcher.handle(request);
        return this;
    }

    @Override
    public NetworkServer head(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.head(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer put(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.put(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer options(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.options(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer post(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.post(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer postWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.postWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer trace(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.trace(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer optionsWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.optionsWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer patch(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.patch(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer connect(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.connect(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer connectWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.connectWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer delete(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.delete(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer deleteWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.deleteWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer all(String pattern, Handler<HttpServerRequest> handler) {
        routeMatcher.all(pattern, handler);
        return this;
    }

    @Override
    public NetworkServer headWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.headWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer traceWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.traceWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer allWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.allWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer patchWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.patchWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer putWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.putWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer getWithRegEx(String regex, Handler<HttpServerRequest> handler) {
        routeMatcher.getWithRegEx(regex, handler);
        return this;
    }

    @Override
    public NetworkServer noMatch(Handler<HttpServerRequest> handler) {
        routeMatcher.noMatch(handler);
        return this;
    }
}
