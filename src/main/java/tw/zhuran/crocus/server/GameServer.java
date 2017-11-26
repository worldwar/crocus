package tw.zhuran.crocus.server;

import io.netty.channel.ChannelFuture;
import tw.zhuran.crocus.server.handler.Handler;
import tw.zhuran.crocus.server.handler.PacketHandler;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.task.GameMatcher;
import tw.zhuran.crocus.util.Meta;
import tw.zhuran.crocus.util.Randoms;

import java.util.concurrent.*;

public class GameServer {
    private Listener listener;
    private PacketHandler handler;

    private ConcurrentMap<Long, GameContext> contexts;

    private ConcurrentMap<Long, GameContext> connectionContexts;

    private Hub hub = new Hub();

    private ExecutorService executor = Executors.newScheduledThreadPool(8);

    public static void main(String[] args) {
        new GameServer().start();
    }

    public GameServer() {
        handler = new PacketHandler(this);
        listener = new Listener(handler);
        contexts = new ConcurrentHashMap<>();
        connectionContexts = new ConcurrentHashMap<>();
    }

    public void start() {
        try {
            ChannelFuture future = listener.listen(10200);
            initTasks();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        }
    }

    private void initTasks() {
        submit(new GameMatcher(this));
    }

    public void newConnection(Connection connection) {
        hub.newConnection(connection);
    }

    public void submit(Callable callable) {
        executor.submit(callable);
    }

    public LinkedBlockingDeque<Connection> matching() {
        return hub.getMatching();
    }

    public void newGame(Connection first, Connection second) {
        GameContext context;
        while (true) {
            long id = Randoms.id();
            context = new GameContext(id, first, second);
            GameContext gameContext = contexts.putIfAbsent(id, context);
            if (gameContext == null) {
                break;
            }
        }
        connectionContexts.putIfAbsent(first.getId(), context);
        connectionContexts.putIfAbsent(second.getId(), context);
        context.start();

    }

    public void dispatch(long id, Packet packet) {
        GameContext context = connectionContexts.get(id);
        if (context != null) {
            Class type = Meta.type(Handler.class, packet.getType());
            if (type != null) {
                Object handler = Meta.create(type, context);
                Meta.call(handler, "handle", packet, null, null);
            }
        }
    }
}

