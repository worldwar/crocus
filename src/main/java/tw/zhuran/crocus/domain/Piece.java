package tw.zhuran.crocus.domain;

public class Piece {
    private int id;
    private Kind kind;
    private Position position;
    private Force force;

    public int getId() {
        return id;
    }

    public Piece setId(int id) {
        this.id = id;
        return this;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }
}
