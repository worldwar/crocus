package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.*;

public class PawnActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Piece piece = action.getPiece();
        Position to = action.getTarget();
        Position from = piece.getPosition();

        return Positions.md(from, to) == 1 &&
                ((piece.red() && to.y >= from.y) || (piece.black() && to.y <= from.y)) &&
                (!Positions.inForceArea(from, piece.getForce()) || to.x == from.x);
    }
}
