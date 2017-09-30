package tw.zhuran.crocus.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    public Piece[][] positions = new Piece[10][11];

    public void place(Piece piece, Position position) {
        piece.setPosition(position);
        positions[position.x][position.y] = piece;
    }

    public void place(Piece piece) {
        Position position = piece.getPosition();
        place(piece, position);
    }

    public static Board from(Formation formation) {
        Board board = new Board();
        formation.pieces.forEach(board::place);
        return board;
    }

    public Piece piece(Position position) {
        return positions[position.x][position.y];
    }

    public boolean empty(Position position) {
        return piece(position) == null;
    }

    public boolean placed(Position position) {
        return !empty(position);
    }

    public boolean empty(List<Position> range) {
        return range.stream().allMatch(this::empty);
    }

    public int count(List<Position> range) {
        return range.stream().filter(this::placed).collect(Collectors.toList()).size();
    }

    public Piece king(Force force) {
        for (Piece[] row : positions) {
            for (Piece piece : row) {
                if (piece != null && piece.getForce() == force && piece.getKind() == Kind.KING) {
                    return piece;
                }
            }
        }
        return null;
    }
}
