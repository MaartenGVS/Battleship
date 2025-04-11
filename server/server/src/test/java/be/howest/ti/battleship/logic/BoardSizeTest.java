package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardSizeTest {

    @Test
    void twoSizesAreEqual() {
        BoardSize board = new BoardSize(12, 10);
        BoardSize boardSameBoardSize = new BoardSize(12, 10);


        assertEquals(board, boardSameBoardSize);
    }

    @Test
    void twoSizesAreNotEqual() {
        BoardSize board = new BoardSize(11, 10);
        BoardSize boardDifferentBoardSize = new BoardSize(16, 18);


        assertNotEquals(board, boardDifferentBoardSize);
    }


    @Test
    void setRows() {
        BoardSize board = new BoardSize(12, 10);
        board.setRows(15);

        assertEquals(15, board.getRows());
    }

    @Test
    void setCols() {
        BoardSize board = new BoardSize(12, 10);
        board.setCols(15);

        assertEquals(15, board.getCols());
    }

    @Test
    void testToString() {
        BoardSize board = new BoardSize(12, 10);

        assertEquals("BoardSize{rows=12, cols=10}", board.toString());
    }

    @Test
    void testEquals() {
        BoardSize board = new BoardSize(12, 10);
        BoardSize boardSameBoardSize = new BoardSize(12, 10);

        assertEquals(board, boardSameBoardSize);
    }

    @Test
    void testHashCode() {
        BoardSize board = new BoardSize(12, 10);
        BoardSize boardSameBoardSize = new BoardSize(12, 10);

        assertEquals(board.hashCode(), boardSameBoardSize.hashCode());
    }


    @Test
    void testValidateBoardSize() {

        assertThrows(IllegalArgumentException.class, () -> new BoardSize(0, 53));
        assertThrows(IllegalArgumentException.class, () -> new BoardSize(1, 6778));
        assertThrows(IllegalArgumentException.class, () -> new BoardSize(4, 55));
        assertThrows(IllegalArgumentException.class, () -> new BoardSize(55, 34));
    }

    @Test
    void testGetRows() {
        BoardSize board = new BoardSize(12, 10);

        assertEquals(12, board.getRows());
    }


}