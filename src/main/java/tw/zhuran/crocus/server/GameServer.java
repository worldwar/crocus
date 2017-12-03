package tw.zhuran.crocus.server;

import io.netty.channel.ChannelFuture;
import tw.zhuran.crocus.server.handler.Handler;
import tw.zhuran.crocus.server.handler.PacketHandler;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.task.GameMatcher;
import tw.zhuran.crocus.server.websocket.WebSocketListener;
import tw.zhuran.crocus.util.Meta;
import tw.zhuran.crocus.util.Randoms;

import java.util.concurrent.*;

public class GameServer {
    private Listener listener;
    private WebSocketListener webSocketListener;
    private PacketHandler handler;

    private ConcurrentMap<Long, GameContext> contexts;

    private ConcurrentMap<Long, GameContext> connectionContexts;

    private Hub hub = new Hub();

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

    public static void main(String[] args) {
        new GameServer().start();
    }

    public GameServer() {
        handler = new PacketHandler(this);
        listener = new Listener(handler);
        webSocketListener = new WebSocketListener(this);
        contexts = new ConcurrentHashMap<>();
        connectionContexts = new ConcurrentHashMap<>();
    }

    public void start() {
        try {
            ChannelFuture future = listener.listen(10200);
            ChannelFuture webSocketFuture = webSocketListener.listen(10222);
            initTasks();
            future.channel().closeFuture().sync();
            webSocketFuture.channel().closeFuture().sync();
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

    public void delay(Callable callable) {
        executor.schedule(callable, 2, TimeUnit.SECONDS);
    }

    public LinkedBlockingDeque<Connection> matching() {
        return hub.getMatching();
    }

    public void newGame(Connection first, Connection second) {
        GameContext context;
        while (true) {
            long id = Randoms.id();
            context = new GameContext(this, id, first, second);
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

    public void close(GameContext gameContext) {
        contexts.remove(gameContext.getId());
        startMatching(gameContext.red());
        startMatching(gameContext.black());
    }

    private void startMatching(Connection c) {
        if (c != null) {
            connectionContexts.remove(c.getId());
            hub.addToMatching(c);
        }
    }

    public void removeConnection(long id) {
        GameContext context = connectionContexts.remove(id);
        if (context != null) {
            Connection connection = hub.get(id);
            context.remove(connection);
        }
        hub.remove(id);
    }
}

