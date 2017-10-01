package tw.zhuran.crocus.plan;

import tw.zhuran.crocus.domain.Board;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.domain.Position;

import java.util.ArrayList;
import java.util.List;

public class GuardPlan extends Plan {

    @Override
    List<Position> positions(Board board, Piece piece) {
        List<Position> positions = new ArrayList<>();
        Position from = piece.getPosition();
        positions.add(from.move(1, 1));
        positions.add(from.move(1, -1));
        positions.add(from.move(-1, 1));
        positions.add(from.move(-1, -1));
        return positions;
    }
}
