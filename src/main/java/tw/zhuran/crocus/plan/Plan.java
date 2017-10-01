package tw.zhuran.crocus.plan;

import tw.zhuran.crocus.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Plan {

   public List<Action> plan(Board board, Piece piece) {
        List<Position> positions = positions(board, piece);
        Position from = piece.getPosition();
        return positions.stream()
                .filter(Position::legal)
                .filter(from::notEquals)
                .map(position -> new Action(piece, position, board.actionType(position)))
                .collect(Collectors.toList());

    }

    abstract List<Position> positions(Board board, Piece piece);
}
