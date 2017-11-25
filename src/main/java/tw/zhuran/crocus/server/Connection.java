package tw.zhuran.crocus.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class Connection {

    private ChannelHandlerContext ctx;

    public Connection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void send(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(byteBuf);
    }
}