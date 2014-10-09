package org.mozilla.browserquest.network;

import io.netty.channel.ChannelHandlerContext;

public class NettyConnection {

    private final ChannelHandlerContext ctx;

    public NettyConnection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
