package tw.zhuran.crocus.domain;

public class Action {
    private Piece piece;
    private Position target;
    private ActionType type;

    public Action(Piece piece, Position target, ActionType type) {
        this.piece = piece;
        this.target = target;
        this.type = type;
    }

    public Piece getPiece() {
        return piece;
    }

    public Action setPiece(Piece piece) {
        this.piece = piece;
        return this;
    }

    public Position getTarget() {
        return target;
    }

    public Action setTarget(Position target) {
        this.target = target;
        return this;
    }

    public ActionType getType() {
        return type;
    }

    public Action setType(ActionType type) {
        this.type = type;
        return this;
    }
}


