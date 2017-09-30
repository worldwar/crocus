package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.rule.action.ActionRule;

public class CheckedActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Force force = action.getPiece().getForce();
        board = board.tryApply(action);
        return !board.checked(force);
    }
}
