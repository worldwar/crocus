package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class KnightRule implements PieceRule {
    @Override
    public boolean legal(Piece piece, Position to) {
        Position from = piece.getPosition();
        return Positions.md(from, to) == 3 &&
                (Positions.xd(from, to) == 1 ||
                Positions.yd(from, to) == 1);
    }
}
