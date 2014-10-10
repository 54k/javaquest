package org.mozilla.browserquest.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.mozilla.browserquest.util.NamingThreadFactory;
import org.vertx.java.core.Handler;

public class NettyProvider {

    private Handler<NettyConnection> connectionHandler;
    private ServerBootstrap serverBootstrap;
    private Channel serverChannel;

    public NettyProvider() {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(new NioEventLoopGroup(2, new NamingThreadFactory("netty-provider")));
        serverBootstrap.childHandler(new TransportInitializer());
    }

    public void bind(int port) {
        validate();
        serverChannel = serverBootstrap.bind(port).syncUninterruptibly().channel();
    }

    private void validate() {
        if (connectionHandler == null) {
            throw new IllegalStateException();
        }
    }

    public void close() {
        serverChannel.close().syncUninterruptibly();
    }

    public void onNewConnection(Handler<NettyConnection> connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    private class TransportInitializer extends ChannelInitializer<NioSocketChannel> {
        @Override
        protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline().addLast(new HttpServerCodec());
            ch.pipeline().addLast(new HttpObjectAggregator(65536));
            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
            ch.pipeline().addLast(new WebSocketAcceptor());
        }
    }

    private class WebSocketAcceptor extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
                connectionHandler.handle(new NettyConnection(ctx));
            }
        }
    }
}