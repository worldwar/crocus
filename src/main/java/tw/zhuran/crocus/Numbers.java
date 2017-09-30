package tw.zhuran.crocus;

import java.util.ArrayList;
import java.util.List;

public class Numbers {

    public static List<Integer> range(int from, int to) {
        int delta = from <= to ? 1 : -1;
        int current = from;
        int length = Math.abs(from - to) - 1;
        ArrayList<Integer> range = new ArrayList<Integer>(length);
        for (int i = 0; i < length; i++) {
            current = current + delta;
            range.add(current);
        }
        return range;
    }

}
