package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Position;

public class RookActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Position from = action.getPiece().getPosition();
        Position to = action.getTarget();
        return Positions.inline(from, to) &&
                board.empty(Positions.range(from, to));
    }
}
