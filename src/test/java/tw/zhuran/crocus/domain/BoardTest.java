package tw.zhuran.crocus.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void before() {
        board = new Board();
    }

    @Test
    public void testCheckmated() throws Exception {
        board.reset();

        board.makeEmpty(new Position(5, 7));

        Piece gun1 = board.piece(new Position(2, 3));
        Piece gun2 = board.piece(new Position(8, 3));

        board.move(gun1, new Position(5, 8));
        board.move(gun2, new Position(5, 6));

        assertTrue(board.checkmated(Force.BLACK));

        Piece blackKnight = board.piece(new Position(8, 10));
        board.move(blackKnight, new Position(7, 8));

        assertFalse(board.checkmated(Force.BLACK));
    }

    @Test
    public void testStalemated() throws Exception {

        Piece redRook = new Piece(1, Kind.ROOK, new Position(9, 9), Force.RED);
        Piece redKing = new Piece(2, Kind.KING, new Position(5, 2), Force.RED);
        Piece blackKing = new Piece(3, Kind.KING, new Position(6, 10), Force.BLACK);

        board.place(redRook);
        board.place(redKing);
        board.place(blackKing);

        assertTrue(board.checkmated(Force.BLACK));

        board.move(redRook, new Position(9, 8));

        assertFalse(board.checkmated(Force.BLACK));
    }
}
