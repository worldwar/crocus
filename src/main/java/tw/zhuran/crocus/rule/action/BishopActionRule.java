package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class BishopActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Piece piece = action.getPiece();
        Position from = piece.getPosition();
        Position to = action.getTarget();

        return Positions.xd(from, to) == 2 &&
                Positions.yd(from, to) == 2 &&
                Positions.inForceArea(to, piece.getForce()) &&
                board.empty(Positions.bishopObstacle(from, to));
    }
}
