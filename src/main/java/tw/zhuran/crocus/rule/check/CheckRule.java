package tw.zhuran.crocus.rule.check;

import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.Boards;
import tw.zhuran.crocus.rule.action.ActionRule;

public class CheckRule {
    public boolean check(Board board, Piece piece) {
        Piece opposedKing = board.king(piece.getForce().opposed());
        Action action = new Action(piece, opposedKing.getPosition(), ActionType.CAPTURE);
        ActionRule rule = Boards.rule(piece.getKind());
        return rule.legal(board, action);
    }
}
