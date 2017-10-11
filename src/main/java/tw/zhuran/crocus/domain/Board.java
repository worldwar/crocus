package tw.zhuran.crocus.domain;

import tw.zhuran.crocus.Boards;
import tw.zhuran.crocus.plan.Plan;
import tw.zhuran.crocus.rule.action.ActionRule;
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

    public ActionType actionType(Position to) {
        return placed(to) ? ActionType.CAPTURE : ActionType.MOVE;
    }

    public boolean checkmated(Force force) {
        List<Piece> pieces = forcePieces(force);
        return pieces.stream().noneMatch(piece -> {
            Plan plan = Boards.plan(piece.getKind());
            boolean legal = plan.plan(this, piece).stream().anyMatch(this::legal);
            return legal;
        });
    }

    public boolean legal(Action action) {
        List<ActionRule> rules = Boards.rulesFor(action.getPiece());
        return rules.stream().allMatch(rule -> rule.legal(this, action));
    }

    public void reset() {
        place(new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED));
        place(new Piece(2, Kind.KNIGHT, new Position(2, 1), Force.RED));
        place(new Piece(3, Kind.BISHOP, new Position(3, 1), Force.RED));
        place(new Piece(4, Kind.GUARD, new Position(4, 1), Force.RED));
        place(new Piece(5, Kind.KING, new Position(5, 1), Force.RED));
        place(new Piece(6, Kind.GUARD, new Position(6, 1), Force.RED));
        place(new Piece(7, Kind.BISHOP, new Position(7, 1), Force.RED));
        place(new Piece(8, Kind.KNIGHT, new Position(8, 1), Force.RED));
        place(new Piece(9, Kind.ROOK, new Position(9, 1), Force.RED));
        place(new Piece(10, Kind.GUN, new Position(2, 3), Force.RED));
        place(new Piece(11, Kind.GUN, new Position(8, 3), Force.RED));
        place(new Piece(12, Kind.PAWN, new Position(1, 4), Force.RED));
        place(new Piece(13, Kind.PAWN, new Position(3, 4), Force.RED));
        place(new Piece(14, Kind.PAWN, new Position(5, 4), Force.RED));
        place(new Piece(15, Kind.PAWN, new Position(7, 4), Force.RED));
        place(new Piece(16, Kind.PAWN, new Position(9, 4), Force.RED));

        place(new Piece(17, Kind.ROOK, new Position(9, 10), Force.BLACK));
        place(new Piece(18, Kind.KNIGHT, new Position(8, 10), Force.BLACK));
        place(new Piece(19, Kind.BISHOP, new Position(7, 10), Force.BLACK));
        place(new Piece(20, Kind.GUARD, new Position(6, 10), Force.BLACK));
        place(new Piece(21, Kind.KING, new Position(5, 10), Force.BLACK));
        place(new Piece(22, Kind.GUARD, new Position(4, 10), Force.BLACK));
        place(new Piece(23, Kind.BISHOP, new Position(3, 10), Force.BLACK));
        place(new Piece(24, Kind.KNIGHT, new Position(2, 10), Force.BLACK));
        place(new Piece(25, Kind.ROOK, new Position(1, 10), Force.BLACK));

        place(new Piece(26, Kind.GUN, new Position(8, 8), Force.BLACK));
        place(new Piece(27, Kind.GUN, new Position(2, 8), Force.BLACK));
        place(new Piece(28, Kind.PAWN, new Position(9, 7), Force.BLACK));
        place(new Piece(29, Kind.PAWN, new Position(7, 7), Force.BLACK));
        place(new Piece(30, Kind.PAWN, new Position(5, 7), Force.BLACK));
        place(new Piece(31, Kind.PAWN, new Position(3, 7), Force.BLACK));
        place(new Piece(32, Kind.PAWN, new Position(1, 7), Force.BLACK));
    }

    public void move(Piece piece, Position position) {
        makeEmpty(piece.getPosition());
        place(piece, position);
    }

    public Action makeAction(Piece piece, Position position) {
        ActionType actionType = actionType(position);
        return new Action(piece, position, actionType);
    }

    public String print() {
        StringBuilder sb = new StringBuilder(128);

        for (int y = 10; y >= 1; y--) {
            for (int x = 9; x >= 1; x--) {
                sb.append(Boards.symbol(positions[x][y]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
