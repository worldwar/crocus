package tw.zhuran.crocus.server;

import io.netty.channel.ChannelFuture;
import tw.zhuran.crocus.server.handler.PacketHandler;

public class GameServer {
    private Listener listener;
    private GameContext gameContext;
    private PacketHandler handler;

    public static void main(String[] args) {
        new GameServer().start();
    }

    public GameServer() {
        gameContext = new GameContext();
        handler = new PacketHandler(gameContext);
        listener = new Listener(handler);
    }

    public void start() {
        try {
            ChannelFuture future = listener.listen(10200);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        }
    }
}

