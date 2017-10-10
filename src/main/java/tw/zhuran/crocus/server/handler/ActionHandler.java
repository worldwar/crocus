package tw.zhuran.crocus.server.handler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tw.zhuran.crocus.server.packet.ActionPacket;
import tw.zhuran.crocus.server.packet.PacketType;

@Handler(PacketType.ACTION)
public class ActionHandler {
    void handle(ActionPacket packet, ChannelHandlerContext ctx, ByteBuf buf) {


    }
}
