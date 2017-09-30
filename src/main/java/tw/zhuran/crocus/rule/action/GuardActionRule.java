package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class GuardActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Piece piece = action.getPiece();
        Position to = action.getTarget();
        return Positions.xd(piece.getPosition(), to) == 1 &&
                Positions.yd(piece.getPosition(), to) == 1 &&
                Positions.inForceArea(to, piece.getForce());
    }
}
