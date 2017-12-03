package tw.zhuran.crocus.server.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import tw.zhuran.crocus.server.Connection;
import tw.zhuran.crocus.server.ConnectionType;
import tw.zhuran.crocus.server.GameServer;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private GameServer gameServer;

    public TextWebSocketFrameHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
            TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        Packet packet = Packets.packetFromJson(text);
        gameServer.dispatch(ctx.hashCode(), packet);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        gameServer.newConnection(new Connection(ctx, ConnectionType.WEB_SOCKET));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        gameServer.removeConnection(ctx.hashCode());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    }
}
