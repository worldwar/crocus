package tw.zhuran.crocus.plan;

import tw.zhuran.crocus.Positions;
import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

import java.util.List;

public class RookPlan extends Plan {

    @Override
    List<Position> positions(Board board, Piece piece) {
        Position from = piece.getPosition();
        List<Position> positions = Positions.row(from);
        positions.addAll(Positions.column(from));
        return positions;
    }
}
