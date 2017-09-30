package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.*;

public class KingActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Position to = action.getTarget();
        Piece piece = action.getPiece();
        Position from = piece.getPosition();
        return Positions.inKingArea(from, piece.getForce()) &&
                Positions.md(from, to) == 1;

    }
}
