package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.domain.*;

public class TargetForceFormationRule implements FormationRule {

    @Override
    public boolean legal(Formation formation, Action action) {
        Board board = formation.board();
        Position position = action.getTarget();
        Piece piece = board.piece(position);
        return piece == null || piece.getForce() != action.getPiece().getForce();
    }
}
