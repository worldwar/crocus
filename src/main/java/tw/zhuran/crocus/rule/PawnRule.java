package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class PawnRule implements PieceRule {
    @Override
    public boolean legal(Piece piece, Position target) {
        Position from = piece.getPosition();

        return Positions.md(from, target) == 1 &&
                target.y - from.y >= 0 &&
                (!Positions.inForceArea(from, piece.getForce()) || target.x == from.x);
    }
}
