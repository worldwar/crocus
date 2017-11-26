package tw.zhuran.crocus.server;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Game;
import tw.zhuran.crocus.domain.GameState;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;
import tw.zhuran.crocus.util.Randoms;

import java.util.List;

public class GameContext {
    private final long id;

    private Game game;

    private Hub hub;

    public GameContext(long id, Connection red, Connection black) {
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
        byte[] bytes = packet.bytes();
        connection.send(bytes);
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
}
