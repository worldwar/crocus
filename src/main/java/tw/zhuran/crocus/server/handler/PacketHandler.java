package tw.zhuran.crocus.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import tw.zhuran.crocus.server.Connection;
import tw.zhuran.crocus.server.GameServer;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;

import java.util.List;

public class PacketHandler extends ByteToMessageDecoder {
    private GameServer gameServer;

    public PacketHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        gameServer.newConnection(new Connection(ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        gameServer.removeConnection(ctx.hashCode());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = Packets.packet(in);
        process(packet, ctx, in);
    }

    private void process(Packet packet, ChannelHandlerContext ctx, ByteBuf in) {
        long id = ctx.hashCode();
        gameServer.dispatch(id, packet);
    }

    public PacketHandler duplicate() {
        return new PacketHandler(gameServer);
    }
}
