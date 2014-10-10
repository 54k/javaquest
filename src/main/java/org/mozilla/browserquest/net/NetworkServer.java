package org.mozilla.browserquest.net;

public class NetworkServer {

    private NettyProvider nettyProvider;

    public NetworkServer() {
        nettyProvider = new NettyProvider();
    }

    public void bind(int port) {
        nettyProvider.bind(port);
    }

    public void close() {
        nettyProvider.close();
    }
}
