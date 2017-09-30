package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;
import tw.zhuran.crocus.rule.action.ActionRule;

public class KingFaceRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        board = board.tryApply(action);
        Piece piece = action.getPiece();
        Position kingPosition = board.king(piece.getForce()).getPosition();
        Position opposedKingPosition = board.king(piece.getForce().opposed())
                .getPosition();
        if (Positions.xd(kingPosition, opposedKingPosition) == 0) {
            return !board.empty(Positions.range(kingPosition, opposedKingPosition));
        }
        return true;
    }
}
