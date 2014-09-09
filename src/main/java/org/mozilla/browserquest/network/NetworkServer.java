package org.mozilla.browserquest.network;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

public interface NetworkServer {

    NetworkServer listen(int port, String host);

    NetworkServer listen(int port);

    NetworkServer close();

    NetworkServer close(Handler<AsyncResult<Void>> doneHandler);

    NetworkServer listen(int port, String host, Handler<AsyncResult<HttpServer>> listenHandler);

    NetworkServer listen(int port, Handler<AsyncResult<HttpServer>> listenHandler);

    NetworkServer get(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer head(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer put(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer options(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer post(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer postWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer trace(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer optionsWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer patch(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer connect(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer connectWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer delete(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer deleteWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer all(String pattern, Handler<HttpServerRequest> handler);

    NetworkServer headWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer traceWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer allWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer patchWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer putWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer getWithRegEx(String regex, Handler<HttpServerRequest> handler);

    NetworkServer noMatch(Handler<HttpServerRequest> handler);
}
