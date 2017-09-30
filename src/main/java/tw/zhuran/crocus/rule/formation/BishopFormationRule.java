package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Formation;
import tw.zhuran.crocus.domain.Position;

public class BishopFormationRule implements FormationRule {

    @Override
    public boolean legal(Formation formation, Action action) {
        Board board = Board.from(formation);
        Position from = action.getPiece().getPosition();
        Position to = action.getTarget();
        Position position = Positions.bishopObstacle(from, to);
        return board.empty(position);
    }
}
