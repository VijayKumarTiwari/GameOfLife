package org.vijay.services;

import org.junit.Test;
import org.vijay.commons.BoardIndex;
import org.vijay.domains.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test class for {@link GameBoardService}
 */
public class GameBoardServiceTests {

    private GameBoardService gameBoardService = new GameBoardService();

    @Test
    public final void testCreateNew() {
        List<BoardIndex> boardIndexList = new ArrayList<>();
        boardIndexList.add(new BoardIndex(0, 1, 3));
        GameBoard gameBoard = gameBoardService.createNew(3, boardIndexList);
        assertNotNull(gameBoard);
        assertEquals(3, gameBoard.getSize().intValue());
        assertNull(gameBoard.getId());
        assertTrue(gameBoard.getBoard().get(0).get(1));
    }

    @Test
    public final void testCalculateNextGen() {
        List<BoardIndex> boardIndexList = new ArrayList<>();
        boardIndexList.add(new BoardIndex(0, 2, 3));
        boardIndexList.add(new BoardIndex(2, 0, 3));
        boardIndexList.add(new BoardIndex(1, 1, 3));
        GameBoard gameBoard = gameBoardService.createNew(3, boardIndexList);
        gameBoard = gameBoardService.calculateNextGen(gameBoard);
        List<List<Boolean>> board = gameBoard.getBoard();
        for (int i = 0; i < 3; ++i) {
            List<Boolean> row = board.get(i);
            for (int j = 0; j < 3; ++j) {
                assertTrue("Every cell in this generation should be alive, but we found " + (i + 1) + "," + (j + 1) + " to be dead", row.get(j));
            }
        }
        gameBoard = gameBoardService.calculateNextGen(gameBoard);
        board = gameBoard.getBoard();
        for (int i = 0; i < 3; ++i) {
            List<Boolean> row = board.get(i);
            for (int j = 0; j < 3; ++j) {
                assertFalse("Every cell in this generation should be dead, but we found " + (i + 1) + "," + (j + 1) + " to be alive", row.get(j));
            }
        }
    }
}
