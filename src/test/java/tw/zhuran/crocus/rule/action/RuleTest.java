package tw.zhuran.crocus.rule.action;

import org.junit.Before;
import org.junit.Test;
import tw.zhuran.crocus.domain.*;

import static org.junit.Assert.*;

public class RuleTest {
    private Board board = new Board();

    private ActionRule rule = new RookActionRule();

    @Before
    public void before() {
        board = new Board();
    }

    @Test
    public void testRookRule() throws Exception {
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(1, 3), Force.BLACK);
        board.place(rook);
        board.place(knight);

        Action moveAction = new Action(rook, new Position(1, 2), ActionType.MOVE);
        Action captureAction = new Action(rook, new Position(1, 3), ActionType.CAPTURE);
        Action illegalAction = new Action(rook, new Position(1, 4), ActionType.MOVE);
        Action illegalAction2 = new Action(rook, new Position(2, 2), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
    }

    @Test
    public void testKingRuleForRedKing() throws Exception {
        rule = new KingActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);

        Action moveAction = new Action(king, new Position(4, 2), ActionType.MOVE);
        Action captureAction = new Action(king, new Position(5, 3), ActionType.CAPTURE);
        Action illegalAction = new Action(king, new Position(3, 3), ActionType.MOVE);
        Action illegalAction2 = new Action(king, new Position(4, 4), ActionType.MOVE);
        Action illegalAction3 = new Action(king, new Position(4, 1), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
        assertFalse(rule.legal(board, illegalAction3));

        board.makeEmpty(king.getPosition());
        board.place(king, new Position(6, 3));

        Action illegalAction4 = new Action(king, new Position(7, 3), ActionType.MOVE);
        Action illegalAction5 = new Action(king, new Position(6, 4), ActionType.MOVE);

        assertFalse(rule.legal(board, illegalAction4));
        assertFalse(rule.legal(board, illegalAction5));
    }

    @Test
    public void testKingRuleForBlackKing() throws Exception {
        rule = new KingActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 8), Force.RED);
        Piece king = new Piece(1, Kind.KING, new Position(4, 8), Force.BLACK);
        board.place(rook);
        board.place(knight);
        board.place(king);

        Action moveAction = new Action(king, new Position(4, 9), ActionType.MOVE);
        Action captureAction = new Action(king, new Position(5, 8), ActionType.CAPTURE);
        Action illegalAction = new Action(king, new Position(5, 9), ActionType.MOVE);
        Action illegalAction2 = new Action(king, new Position(3, 8), ActionType.MOVE);
        Action illegalAction3 = new Action(king, new Position(4, 7), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
        assertFalse(rule.legal(board, illegalAction3));

        board.makeEmpty(king.getPosition());
        board.place(king, new Position(6, 8));

        Action illegalAction4 = new Action(king, new Position(7, 8), ActionType.MOVE);
        Action illegalAction5 = new Action(king, new Position(6, 7), ActionType.MOVE);

        assertFalse(rule.legal(board, illegalAction4));
        assertFalse(rule.legal(board, illegalAction5));
    }

    @Test
    public void testKnightRule() throws Exception {
        rule = new KnightActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        Piece pawn = new Piece(1, Kind.PAWN, new Position(6, 5), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);
        board.place(pawn);

        Action moveAction = new Action(knight, new Position(4, 5), ActionType.MOVE);

        Action captureAction = new Action(knight, new Position(6, 5), ActionType.CAPTURE);
        Action illegalAction = new Action(knight, new Position(7, 5), ActionType.MOVE);
        Action obstacleActionByKing1 = new Action(knight, new Position(3, 4), ActionType.MOVE);
        Action obstacleActionByKing2 = new Action(knight, new Position(3, 2), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, obstacleActionByKing1));
        assertFalse(rule.legal(board, obstacleActionByKing2));

        board.makeEmpty(king.getPosition());

        assertTrue(rule.legal(board, obstacleActionByKing1));
        assertTrue(rule.legal(board, obstacleActionByKing2));
    }

    @Test
    public void testBishopRule() throws Exception {
        rule = new BishopActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        Piece pawn = new Piece(1, Kind.PAWN, new Position(6, 5), Force.RED);
        Piece bishop = new Piece(1, Kind.BISHOP, new Position(7, 1), Force.RED);
        Piece gun = new Piece(1, Kind.GUN, new Position(8, 4), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);
        board.place(pawn);
        board.place(bishop);
        board.place(gun);

        Action moveAction = new Action(bishop, new Position(9, 3), ActionType.MOVE);

        Action captureAction = new Action(bishop, new Position(5, 3), ActionType.CAPTURE);
        Action illegalAction = new Action(bishop, new Position(9, 4), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));

        board.makeEmpty(bishop.getPosition());

        board.place(bishop, new Position(7, 5));

        Action illegalAction2 = new Action(bishop, new Position(9, 7), ActionType.MOVE);

        assertFalse(rule.legal(board, moveAction));
        assertFalse(rule.legal(board, illegalAction2));
    }

    @Test
    public void testGuardRule() throws Exception {
        rule = new GuardActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        Piece pawn = new Piece(1, Kind.PAWN, new Position(6, 5), Force.RED);
        Piece bishop = new Piece(1, Kind.BISHOP, new Position(7, 1), Force.RED);
        Piece gun = new Piece(1, Kind.GUN, new Position(8, 4), Force.RED);
        Piece guard = new Piece(1, Kind.GUARD, new Position(6, 3), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);
        board.place(pawn);
        board.place(bishop);
        board.place(gun);
        board.place(guard);

        Action moveAction = new Action(guard, new Position(5, 2), ActionType.MOVE);
        Action illegalAction = new Action(guard, new Position(7, 2), ActionType.MOVE);
        Action illegalAction2 = new Action(guard, new Position(7, 4), ActionType.MOVE);
        Action illegalAction3 = new Action(guard, new Position(5, 4), ActionType.MOVE);
        Action illegalAction4 = new Action(guard, new Position(6, 2), ActionType.MOVE);
        Action illegalAction5 = new Action(guard, new Position(5, 3), ActionType.CAPTURE);

        assertTrue(rule.legal(board, moveAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
        assertFalse(rule.legal(board, illegalAction3));
        assertFalse(rule.legal(board, illegalAction4));
        assertFalse(rule.legal(board, illegalAction5));
    }

    @Test
    public void testGunRule() throws Exception {
        rule = new GunActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        Piece pawn = new Piece(1, Kind.PAWN, new Position(6, 5), Force.RED);
        Piece pawn2 = new Piece(1, Kind.PAWN, new Position(9, 2), Force.BLACK);
        Piece bishop = new Piece(1, Kind.BISHOP, new Position(7, 1), Force.RED);
        Piece gun = new Piece(1, Kind.GUN, new Position(9, 3), Force.RED);
        Piece guard = new Piece(1, Kind.GUARD, new Position(6, 3), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);
        board.place(pawn);
        board.place(bishop);
        board.place(gun);
        board.place(guard);
        board.place(pawn2);


        Action moveAction = new Action(gun, new Position(8, 3), ActionType.MOVE);
        Action moveAction2 = new Action(gun, new Position(9, 2), ActionType.MOVE);
        Action captureAction = new Action(gun, new Position(5, 3), ActionType.CAPTURE);
        Action illegalAction = new Action(gun, new Position(8, 2), ActionType.MOVE);
        Action illegalAction2 = new Action(gun, new Position(9, 1), ActionType.MOVE);
        Action illegalAction3 = new Action(gun, new Position(4, 3), ActionType.CAPTURE);
        Action illegalAction4 = new Action(gun, new Position(9, 2), ActionType.CAPTURE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, moveAction2));
        assertTrue(rule.legal(board, captureAction));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
        assertFalse(rule.legal(board, illegalAction3));
        assertFalse(rule.legal(board, illegalAction4));
    }

    @Test
    public void testPawnRule() throws Exception {
        rule = new PawnActionRule();
        Piece rook = new Piece(1, Kind.ROOK, new Position(1, 1), Force.RED);
        Piece knight = new Piece(1, Kind.KNIGHT, new Position(5, 3), Force.BLACK);
        Piece king = new Piece(1, Kind.KING, new Position(4, 3), Force.RED);
        Piece pawn = new Piece(1, Kind.PAWN, new Position(7, 5), Force.RED);
        Piece pawn2 = new Piece(1, Kind.PAWN, new Position(9, 2), Force.BLACK);
        Piece bishop = new Piece(1, Kind.BISHOP, new Position(7, 1), Force.RED);
        Piece gun = new Piece(1, Kind.GUN, new Position(9, 3), Force.RED);
        Piece guard = new Piece(1, Kind.GUARD, new Position(6, 3), Force.RED);
        board.place(rook);
        board.place(knight);
        board.place(king);
        board.place(pawn);
        board.place(bishop);
        board.place(gun);
        board.place(guard);
        board.place(pawn2);


        Action moveAction = new Action(pawn, new Position(7, 6), ActionType.MOVE);
        Action moveAction2 = new Action(pawn2, new Position(9, 1), ActionType.MOVE);
        Action moveAction3 = new Action(pawn2, new Position(8, 2), ActionType.MOVE);
        Action illegalAction = new Action(pawn, new Position(8, 5), ActionType.MOVE);
        Action illegalAction2 = new Action(pawn, new Position(7, 4), ActionType.MOVE);
        Action illegalAction3 = new Action(pawn, new Position(7, 7), ActionType.MOVE);
        Action illegalAction4 = new Action(pawn2, new Position(9, 3), ActionType.MOVE);
        Action illegalAction5 = new Action(pawn2, new Position(7, 2), ActionType.MOVE);

        assertTrue(rule.legal(board, moveAction));
        assertTrue(rule.legal(board, moveAction2));
        assertTrue(rule.legal(board, moveAction3));
        assertFalse(rule.legal(board, illegalAction));
        assertFalse(rule.legal(board, illegalAction2));
        assertFalse(rule.legal(board, illegalAction3));
        assertFalse(rule.legal(board, illegalAction4));
        assertFalse(rule.legal(board, illegalAction5));
    }
}
