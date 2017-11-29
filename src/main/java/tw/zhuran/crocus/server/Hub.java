package tw.zhuran.crocus.server;

import com.google.common.collect.Lists;
import tw.zhuran.crocus.domain.Force;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Hub {
    private Connection red;
    private Connection black;
    private List<Connection> spectators = new ArrayList<>();
    private ConcurrentMap<Long, Connection> connections = new ConcurrentHashMap<>();
    private LinkedBlockingDeque<Connection> matching = new LinkedBlockingDeque<>();

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

    public Connection newConnection(Connection connection) {
        connections.putIfAbsent(connection.getId(), connection);
        addToMatching(connection);
        return connection;
    }

    public void add(Connection connection) {
        connections.putIfAbsent(connection.getId(), connection);
    }

    public LinkedBlockingDeque<Connection> getMatching() {
        return matching;
    }

    public void switchRedBlack() {
        Connection c = red;
        red = black;
        black = c;
    }

    public void addToMatching(Connection c) {
        if (!matching.contains(c)) {
            try {
                matching.put(c);
            } catch (Exception e) {

            }
        }
    }

    public void remove(long id) {
        Connection c = connections.remove(id);
        matching.remove(c);
        spectators.remove(c);
        if (red == c) {
            red = null;
        }
        if (black == c) {
            black = null;
        }
    }

    public Connection get(long id) {
        return connections.get(id);
    }
}
