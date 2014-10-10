package org.mozilla.browserquest.net;

public interface NetworkClientFactory {
    NetworkClient createClient(NettyConnection connection);
}
