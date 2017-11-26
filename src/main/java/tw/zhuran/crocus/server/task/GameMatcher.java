package tw.zhuran.crocus.server.task;

import tw.zhuran.crocus.server.Connection;
import tw.zhuran.crocus.server.GameServer;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

public class GameMatcher implements Callable<Void> {
    private GameServer gameServer;

    public GameMatcher(GameServer gameServer) {
        this.gameServer = gameServer;
    }
    @Override
    public Void call() throws Exception {
        LinkedBlockingDeque<Connection> matching = gameServer.matching();
        Connection first = matching.take();
        Connection second = matching.take();
        gameServer.newGame(first, second);
        gameServer.submit(this);
        return null;
    }
}
