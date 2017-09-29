package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class KnightRule implements PieceRule {
    @Override
    public boolean legal(Piece piece, Position target) {
        return Positions.md(piece.getPosition(), target) == 3;
    }
}
