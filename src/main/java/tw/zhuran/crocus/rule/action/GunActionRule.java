package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.*;
import tw.zhuran.crocus.Boards;

import java.util.List;

public class GunActionRule implements ActionRule {

    @Override
    public boolean legal(Board board, Action action) {
        Piece piece = action.getPiece();
        Position to = action.getTarget();
        if (action.getType() == ActionType.MOVE) {
            ActionRule rule = Boards.rule(Kind.ROOK);
            return rule.legal(board, action);
        } else {
            boolean inline = Positions.inline(piece.getPosition(), to);
            if (!inline) return false;
            List<Position> range = Positions
                    .range(piece.getPosition(), to);
            return board.count(range) == 1;
        }
    }
}
