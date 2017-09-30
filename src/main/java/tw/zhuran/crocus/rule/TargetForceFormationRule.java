package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.rule.action.ActionRule;

public class TargetForceFormationRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Position position = action.getTarget();
        Piece piece = board.piece(position);
        return piece == null || piece.getForce() != action.getPiece().getForce();
    }
}
