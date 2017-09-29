package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public class KingRule implements PieceRule {
    @Override
    public boolean legal(Piece piece, Position target) {
        Position from = piece.getPosition();
        return Positions.inKingArea(from, piece.getForce()) &&
                Positions.md(from, target) == 1;
    }
}
