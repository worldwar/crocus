package tw.zhuran.crocus.domain;

public class Position {
    public int x;
    public int y;

    public boolean xin(int a, int b) {
        if (b > a) {
            int t = a;
            a = b;
            b = t;
        }
        return x >= a && x <= b;
    }

    public boolean yin(int a, int b) {
        if (b > a) {
            int t = a;
            a = b;
            b = t;
        }
        return y >= a && y <= b;
    }
}
