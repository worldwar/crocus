package tw.zhuran.crocus.domain;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean xin(int a, int b) {
        if (b < a) {
            int t = a;
            a = b;
            b = t;
        }
        return x >= a && x <= b;
    }

    public boolean yin(int a, int b) {
        if (b < a) {
            int t = a;
            a = b;
            b = t;
        }
        return y >= a && y <= b;
    }

    public Position move(int deltaX, int deltaY) {
        return new Position(x + deltaX, y + deltaY);
    }

    public boolean legal() {
        return x >= 1 &&
                x <= 9 &&
                y >= 1 &&
                y <= 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Position position = (Position) o;

        if (x != position.x)
            return false;
        return y == position.y;
    }

    public boolean notEquals(Object o) {
        return !equals(o);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
