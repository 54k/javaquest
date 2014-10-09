package org.mozilla.browserquest.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.vertx.java.core.Handler;

public class NettyConnection {

    private ChannelHandlerContext ctx;

    private Handler<Void> closeHandler;
    private Handler<String> messageHandler;

    public NettyConnection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.ctx.pipeline().addLast(new WebSocketHandler());
    }

    public void write(String msg) {
        ctx.writeAndFlush(new TextWebSocketFrame(msg));
    }

    public void close() {
        ctx.writeAndFlush(new CloseWebSocketFrame()).addListener(f -> ctx.close());
    }

    public void onMessage(Handler<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void onClose(Handler<Void> closeHandler) {
        this.closeHandler = closeHandler;
    }

    private class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        @Override
        public boolean acceptInboundMessage(Object msg) throws Exception {
            return msg instanceof TextWebSocketFrame;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            messageHandler.handle(msg.text());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            closeHandler.handle(null);
        }
    }
}
