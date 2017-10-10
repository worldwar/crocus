package tw.zhuran.crocus.server;

import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandlerContext;
import tw.zhuran.crocus.domain.Force;

import java.util.ArrayList;
import java.util.List;

public class Hub {
    private Connection red;
    private Connection black;
    private List<Connection> spectators = new ArrayList<>();

    public Connection getRed() {
        return red;
    }

    public void setRed(Connection red) {
        this.red = red;
    }

    public Connection getBlack() {
        return black;
    }

    public void setBlack(Connection black) {
        this.black = black;
    }

    public List<Connection> getSpectators() {
        return spectators;
    }

    public void setSpectators(List<Connection> spectators) {
        this.spectators = spectators;
    }

    public Connection force(Force force) {
        if (force == Force.RED) {
            return red;
        } else {
            return black;
        }
    }

    public List<Connection> playerAndSpectators(Force force) {
        ArrayList<Connection> connections = Lists.newArrayList(spectators);
        connections.add(force(force));
        return connections;
    }

    public List<Connection> all() {
        ArrayList<Connection> connections = Lists.newArrayList(spectators);
        connections.add(red);
        connections.add(black);
        return connections;
    }

    public static Connection newConnection(ChannelHandlerContext ctx) {
        return new Connection(ctx);
    }

    public void add(Connection connection) {
        if (red == null) {
            red = connection;
        } else if (black == null) {
            black = connection;
        } else {
            spectators.add(connection);
        }
    }
}
