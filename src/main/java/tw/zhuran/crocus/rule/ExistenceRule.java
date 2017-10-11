package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.rule.action.ActionRule;

public class ExistenceRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        return board.exists(action.getPiece()) && board.actionType(action.getTarget()) == action.getType();
    }
}
