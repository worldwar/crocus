package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class GuardRule implements PieceRule{
    @Override
    public boolean legal(Piece piece, Position target) {
        return Positions.md(piece.getPosition(), target) == 2 &&
                Positions.inForceArea(target, piece.getForce());
    }
}
