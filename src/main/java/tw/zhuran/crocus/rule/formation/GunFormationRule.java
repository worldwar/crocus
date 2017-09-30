package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.*;

import java.util.List;

public class GunFormationRule implements FormationRule {

    @Override
    public boolean legal(Formation formation, Action action) {
        if (action.getType() == ActionType.MOVE) {
            return new RookFormationRule().legal(formation, action);
        } else {
            Board board = Board.from(formation);
            List<Position> range = Positions
                    .range(action.getPiece().getPosition(), action.getTarget());
            return board.count(range) == 1;
        }
    }
}
