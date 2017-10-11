package tw.zhuran.crocus.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import tw.zhuran.crocus.server.Connection;
import tw.zhuran.crocus.server.GameContext;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;
import tw.zhuran.crocus.util.Meta;

import java.util.List;

public class PacketHandler extends ByteToMessageDecoder {
    private GameContext gameContext;

    public PacketHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        gameContext.add(new Connection(ctx));
        gameContext.tryStart();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = Packets.packet(in);
        process(packet, ctx, in);
    }

    private void process(Packet packet, ChannelHandlerContext ctx, ByteBuf in) {
        Class type = Meta.type(Handler.class, packet.getType());
        if (type != null) {
            Object handler = Meta.create(type, gameContext);
            Meta.call(handler, "handle", packet, ctx, in);
        }
    }

    public GameContext getGameContext() {
        return gameContext;
    }
}
