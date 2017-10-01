package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.rule.action.ActionRule;

public class PositionChangeRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        return !action.getPiece().getPosition().equals(action.getTarget());
    }
}
