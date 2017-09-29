package tw.zhuran.crocus.rule;

import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

public interface PieceRule {
    boolean legal(Piece piece, Position target);
}
