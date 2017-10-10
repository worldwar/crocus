package tw.zhuran.crocus.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import tw.zhuran.crocus.server.Connection;
import tw.zhuran.crocus.server.Hub;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.util.Meta;

import java.util.List;

public class PacketHandler extends ByteToMessageDecoder {

    private Hub hub;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Connection connection = hub.newConnection(ctx);
        hub.add(connection);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (out.size() > 0) {
            Packet packet = (Packet) out.get(0);
            process(packet, ctx, in);
        }
    }

    private void process(Packet packet, ChannelHandlerContext ctx, ByteBuf in) {
        Class type = Meta.type(Handler.class, packet.getType());
        if (type != null) {
            try {
                Object handler = type.newInstance();
                Meta.call(handler, "handle", packet, ctx, in);
            } catch (InstantiationException | IllegalAccessException e) {
            }
        }
    }
}
