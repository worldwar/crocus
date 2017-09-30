package tw.zhuran.crocus.domain;

import java.util.List;

public class Formation {
    public List<Piece> pieces;

    public Board board() {
        return Board.from(this);
    }
}
