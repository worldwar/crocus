package tw.zhuran.crocus.server.handler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tw.zhuran.crocus.server.GameContext;
import tw.zhuran.crocus.server.packet.ActionPacket;
import tw.zhuran.crocus.server.packet.PacketType;

@Handler(PacketType.ACTION)
public class ActionHandler {
    private GameContext gameContext;

    public ActionHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void handle(ActionPacket packet, ChannelHandlerContext ctx, ByteBuf buf) {
        gameContext.play(packet.action());
    }
}
