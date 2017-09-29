package tw.zhuran.crocus;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Position;

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
}
