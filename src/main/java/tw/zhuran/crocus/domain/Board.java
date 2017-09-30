package tw.zhuran.crocus.domain;

import com.google.common.base.Predicates;
import tw.zhuran.crocus.rule.check.CheckRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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

    public List<Piece> select(Predicate<Piece> p) {
        List<Piece> pieces = new ArrayList<>();
        for (Piece[] row : positions) {
            for (Piece piece : row) {
                if (piece != null) {
                    if (p.test(piece)) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }

    public Piece selectOne(Predicate<Piece> p) {
        List<Piece> pieces = select(p);
        if (pieces.size() > 0) {
            return pieces.get(0);
        }
        return null;
    }

    public Piece king(Force force) {
        return selectOne(piece -> piece.getForce() == force && piece.getKind() == Kind.KING);
    }

    public List<Piece> forcePieces(Force force) {
        return select(piece -> piece.getForce() == force);
    }

    public List<Piece> all() {
        return select(piece -> true);
    }

    public List<Piece> red() {
        return forcePieces(Force.RED);
    }

    public List<Piece> black() {
        return forcePieces(Force.BLACK);
    }



    public boolean checked(Force force) {
        List<Piece> pieces = forcePieces(force.opposed());
        CheckRule checkRule = new CheckRule();
        return pieces.stream().anyMatch(piece -> checkRule.check(this, piece));
    }

    public void apply(Action action) {
        Piece piece = piece(action.getPiece().getPosition());
        Position from = piece.getPosition();
        Position to = action.getTarget();
        if (action.getType() == ActionType.CAPTURE) {
            makeEmpty(to);
        }
        piece.setPosition(to);
        makeEmpty(from);
        place(piece, to);
    }

    public void makeEmpty(Position p) {
        positions[p.x][p.y] = null;
    }

    public Board tryApply(Action action) {
        Board board = duplicate();
        board.apply(action);
        return board;
    }

    private Board duplicate() {
        Board board = new Board();
        all().stream().forEach(piece -> board.place(piece.duplicate()));
        return board;
    }
}
