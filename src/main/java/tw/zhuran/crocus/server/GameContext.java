package tw.zhuran.crocus.server;

import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;
import tw.zhuran.crocus.util.Randoms;

import java.util.List;

public class GameContext {
    private final long id;

    private Game game;

    private Hub hub;

    private GameServer gameServer;

    public GameContext(GameServer gameServer, long id, Connection red, Connection black) {
        this.gameServer = gameServer;
        this.id = id;
        this.game = new Game();
        this.hub = new Hub();
        hub.add(red);
        hub.add(black);
        hub.setRed(red);
        hub.setBlack(black);
    }

    public void play(Action action) {
        boolean done = game.play(action.getPiece(), action.getTarget());
        if (done) {
            game.prinit();
            notifyAction(action);
            if (game.getState() == GameState.ENDED) {
                endGame(game.getResult(), game.getReason());
            }
        }
     }

    private void notifyAction(Action action) {
        Packet packet = Packets.packet(action);
        Force force = action.getPiece().getForce().opposed();
        notify(packet, hub.playerAndSpectators(force));
    }

    private void notify(Packet packet, List<Connection> connections) {
        connections.stream()
                .filter(connection -> connection != null)
                .forEach(connection -> notify(packet, connection));
    }

    private void notify(Packet packet, Connection connection) {
        connection.send(packet);
    }

    public void add(Connection connection) {
        hub.add(connection);
    }

    public void add(Connection... connections) {
        for (Connection c : connections) {
            hub.add(c);
        }
    }

    public void start() {
        if (game.getState() == GameState.NOT_STARTED) {
            game.start();
            if(Randoms.probability(0.5f)) {
                hub.switchRedBlack();
            }
            notify(Packets.startGame(Force.RED), hub.getRed());
            notify(Packets.startGame(Force.BLACK), hub.getBlack());
            game.prinit();
        }
    }

    public long getId() {
        return id;
    }

    public Connection red() {
        return hub.getRed();
    }

    public Connection black() {
        return hub.getBlack();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GameContext that = (GameContext) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public void remove(Connection connection) {
        if (game.getState() == GameState.STARTED) {
            if (connection == hub.getRed()) {
                hub.remove(connection.getId());
                endGame(GameResult.BLACK_WIN, GameEndReason.RUNAWAY);
            } else if (connection == hub.getBlack()){
                hub.remove(connection.getId());
                endGame(GameResult.RED_WIN, GameEndReason.RUNAWAY);
            }
        }
    }

    private void endGame(GameResult result, GameEndReason reason) {
        Packet packet = Packets.endGame(result, reason);
        notify(packet, hub.all());
        gameServer.close(this);
    }
}
