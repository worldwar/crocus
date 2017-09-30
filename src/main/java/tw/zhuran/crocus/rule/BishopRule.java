package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class BishopRule implements PieceRule {
    @Override
    public boolean legal(Piece piece, Position target) {
        return Positions.xd(piece.getPosition(), target) == 2 &&
                Positions.yd(piece.getPosition(), target) == 2 &&
                Positions.inForceArea(target, piece.getForce());
    }
}
