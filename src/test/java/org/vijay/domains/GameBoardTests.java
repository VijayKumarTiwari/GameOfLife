package org.vijay.domains;

import org.junit.Test;
import org.vijay.commons.BoardIndex;
import org.vijay.commons.ErrorCode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test class for {@link GameBoard}.
 */
public class GameBoardTests {
    @Test
    public final void testNewBoard() {
        List<BoardIndex> aliveCells = new ArrayList<>();
        aliveCells.add(new BoardIndex(0, 2, 3));
        aliveCells.add(new BoardIndex(2, 0, 3));
        aliveCells.add(new BoardIndex(1, 1, 3));
        GameBoard gameBoard = new GameBoard();
        gameBoard.initBoard(3, aliveCells);
        assertEquals(3, gameBoard.getSize().intValue());
        List<List<Boolean>> board = gameBoard.getBoard();
        for (int i = 0; i < 3; ++i) {
            List<Boolean> row = board.get(i);
            for (int j = 0; j < 3; ++j) {
                if ((i == 0 && j == 2) || (i == 2 && j == 0) || (i == 1 && j == 1)) {
                    assertTrue("This is a alive cell should be true", row.get(j));
                } else {
                    assertFalse("This is a dead cell should be false", row.get(j));
                }
            }
        }
        assertTrue(gameBoard.isAliveCellsPresent());
    }

    @Test
    public final void testSetGetId() {
        List<BoardIndex> aliveCells = new ArrayList<>();
        aliveCells.add(new BoardIndex(0, 2, 3));
        GameBoard gameBoard = new GameBoard();
        gameBoard.initBoard(3, aliveCells);
        assertNotNull(gameBoard.getBoard());
        gameBoard.setId(1);
        assertEquals(1, gameBoard.getId().intValue());
    }

    @Test
    public final void testInvalidInitialConfig() {
        List<BoardIndex> aliveCells = new ArrayList<>();
        aliveCells.add(new BoardIndex(0, 2, 3));
        GameBoard gameBoard = new GameBoard();
        try {
            gameBoard.initBoard(2, aliveCells);
        } catch (RuntimeException e) {
            assertEquals(ErrorCode.INVALID_SIZE_OR_INDEX.getMessageCode(), e.getMessage());
        }
        try {
            gameBoard.initBoard(3, null);
        } catch (RuntimeException e) {
            assertEquals(ErrorCode.INVALID_SIZE_OR_INDEX.getMessageCode(), e.getMessage());
        }
        gameBoard.initBoard(3, aliveCells);
        assertTrue("There should had been no exceptions", true);
    }
}
