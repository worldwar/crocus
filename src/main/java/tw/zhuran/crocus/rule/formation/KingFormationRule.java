package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.*;

public class KingFormationRule implements FormationRule {

    @Override
    public boolean legal(Formation formation, Action action) {
        Position to = action.getTarget();

        Board board = formation.board();
        Position opposedKingPosition = board.king(action.getPiece().getForce().opposed())
                .getPosition();
        if (Positions.xd(opposedKingPosition, to) == 0) {
            return !board.empty(Positions.range(to, opposedKingPosition));
        }
        return true;
    }
}
