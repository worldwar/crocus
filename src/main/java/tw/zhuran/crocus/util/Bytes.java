package tw.zhuran.crocus.util;

public class Bytes {

    public static int window(byte b, int from, int to) {
        assert from >= 0 && from <= 7;
        assert to >= 0 && to <= 7;
        assert from <= to;

        int index = from;
        int result = 0;
        while (index <= to) {
            result = result << 2;
            if ((byte) (b & (1 << index)) != 0) {
                result += 1;
            }
            index++;
        }
        return result;
    }
}
