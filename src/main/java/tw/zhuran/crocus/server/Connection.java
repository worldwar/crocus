package tw.zhuran.crocus.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import tw.zhuran.crocus.server.packet.Packet;

public class Connection {
    private long id;

    private ChannelHandlerContext ctx;

    private ConnectionType type;

    public Connection(ChannelHandlerContext ctx) {
        this(ctx, ConnectionType.TCP);
    }

    public Connection(ChannelHandlerContext ctx, ConnectionType type) {
        this.ctx = ctx;
        this.id = ctx.hashCode();
        this.type = type;
    }

    public void send(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(byteBuf);
    }

    public void send(Packet packet) {
        byte[] bytes;

        if (type == ConnectionType.TCP) {
            bytes = packet.bytes();
            send(bytes);
        } else {
            ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(packet)));
        }
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Connection that = (Connection) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
