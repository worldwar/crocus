package tw.zhuran.crocus.server;

import io.netty.channel.ChannelHandlerContext;

public class Connection {

    private ChannelHandlerContext ctx;

    public Connection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void send(byte[] bytes) {
        ctx.writeAndFlush(bytes);
    }
}
