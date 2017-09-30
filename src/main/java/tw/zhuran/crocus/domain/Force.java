package tw.zhuran.crocus.domain;

public enum Force {
    RED,
    BLACK;

    public Force opposed() {
        return this == RED ? BLACK : RED;
    }
}
