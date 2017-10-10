package tw.zhuran.crocus.server;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Game;
import tw.zhuran.crocus.server.packet.Packet;
import tw.zhuran.crocus.server.packet.Packets;

import java.util.List;

public class GameContext {
    private Game game;

    private Hub hub;

    public void play(Action action) {
        boolean done = game.play(action.getPiece(), action.getTarget());
        if (done) {
            notifyAction(action);
        }
    }

    private void notifyAction(Action action) {
        Packet packet = Packets.packet(action);
        Force force = action.getPiece().getForce().opposed();
        notify(packet, hub.playerAndSpectators(force));
    }

    private void notify(Packet packet, List<Connection> connections) {
        for (Connection connection : connections) {
            if (connection != null) {
                notify(packet, connection);
            }
        }
    }

    private void notify(Packet packet, Connection connection) {
        byte[] bytes = packet.bytes();
        connection.send(bytes);
    }
}
