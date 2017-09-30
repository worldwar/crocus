package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Formation;
import tw.zhuran.crocus.domain.Position;

import java.util.List;

public class RookFormationRule implements FormationRule {

    @Override
    public boolean legal(Formation formation, Action action) {
        List<Position> range = Positions.range(action.getPiece().getPosition(), action.getTarget());
        Board board = Board.from(formation);
        return board.empty(range);
    }
}
