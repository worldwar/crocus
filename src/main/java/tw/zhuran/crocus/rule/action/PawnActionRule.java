package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class PawnActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Piece piece = action.getPiece();
        Position to = action.getTarget();
        Position from = piece.getPosition();

        return Positions.md(from, to) == 1 &&
                to.y - from.y >= 0 &&
                (!Positions.inForceArea(from, piece.getForce()) || to.x == from.x);
    }
}
