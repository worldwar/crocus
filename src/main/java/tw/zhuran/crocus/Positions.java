package tw.zhuran.crocus;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Positions {
    public static boolean inline(Position from, Position to) {
        return from.x == to.x || from.y == to.y;
    }

    public static boolean inForceArea(Position position, Force force) {
        if (force == Force.RED) {
            return position.y <= 5;
        } else {
            return position.y >= 6;
        }
    }

    public static boolean inKingArea(Position position, Force force) {
        if (force == Force.RED) {
            return position.xin(4, 6) && position.yin(1, 3);
        } else {
            return position.xin(4, 6) && position.yin(8, 10);
        }
    }

    public static int xd(Position from, Position to) {
        return Math.abs(from.x - to.x);
    }

    public static int yd(Position from, Position to) {
        return Math.abs(from.y - to.y);
    }

    public static int md(Position from, Position to) {
        return xd(from, to) + yd(from, to);
    }

    public static List<Position> range(Position from, Position to) {
        assert inline(from, to);

        ArrayList<Position> positions = new ArrayList<Position>();
        if (from.x == to.x) {
            return Numbers.range(from.y, to.y).stream().map(y -> new Position(from.x, y)).collect(
                    Collectors.toList());
        } else {
            return Numbers.range(from.x, to.x).stream().map(x -> new Position(x, from.y)).collect(
                    Collectors.toList());
        }
    }

    public static Position knightObstacle(Position from, Position to) {
        if (xd(from, to) == 1) {
            return range(from, new Position(from.x, to.y)).get(0);
        } else {
            return range(from, new Position(to.x, from.y)).get(0);
        }
    }

    public static Position bishopObstacle(Position from, Position to) {
        return new Position((from.x + to.x) / 2, (from.y + to.y) / 2);
    }

    public static List<Position> row(int y) {
        return range(new Position(1, y), new Position(9, y));
    }

    public static List<Position> row(Position position) {
        return row(position.y);
    }

    public static List<Position> column(int x) {
        return range(new Position(x, 1), new Position(x, 10));
    }

    public static List<Position> column(Position position) {
        return column(position.x);
    }
}
