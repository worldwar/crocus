package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Position;

public class KnightActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Position from = action.getPiece().getPosition();
        Position to = action.getTarget();

        return Positions.md(from, to) == 3 &&
                (Positions.xd(from, to) == 1 || Positions.yd(from, to) == 1) &&
                board.empty(Positions.knightObstacle(from, to));
    }
}
