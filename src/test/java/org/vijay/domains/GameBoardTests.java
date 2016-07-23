package org.vijay.domains;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vijayt on 7/23/2016.
 */
public class GameBoardTests {
    @Test
    public final void testNewBoard() {
        List<String> aliveCells = new ArrayList<>();
        aliveCells.add("1,3");
        aliveCells.add("3,1");
        aliveCells.add("2,2");
        GameBoard gameBoard = new GameBoard(3, aliveCells);
        assertEquals(3, gameBoard.getSize());
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
    }

    @Test
    public final void testInvalidInitialConfig() {
        List<String> aliveCells = new ArrayList<>();
        aliveCells.add("1,3");
        try {
            GameBoard gameBoard = new GameBoard(2, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid board size or alive indexes", e.getMessage());
        }
        try {
            GameBoard gameBoard = new GameBoard(3, null);
        } catch (RuntimeException e) {
            assertEquals("Invalid board size or alive indexes", e.getMessage());
        }
        GameBoard gameBoard = new GameBoard(3, aliveCells);
        assertTrue("There should had been no exceptions", true);
    }

    @Test
    public final void testInvalidIndex() {
        List<String> aliveCells = new ArrayList<>();
        aliveCells.add("1,3,1");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid index provided", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("1");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid index provided", e.getMessage());
        }
    }

    @Test
    public final void testInvalidRowCol() {
        List<String> aliveCells = new ArrayList<>();
        aliveCells.add("1,4");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("4,3");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("0,3");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("1,0");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("-1,3");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
        aliveCells.clear();
        aliveCells.add("1,-1");
        try {
            GameBoard gameBoard = new GameBoard(3, aliveCells);
        } catch (RuntimeException e) {
            assertEquals("Invalid row/col number", e.getMessage());
        }
    }

    @Test
    public final void testCalculateNextGeneration() {
        List<String> aliveCells = new ArrayList<>();
        aliveCells.add("1,3");
        aliveCells.add("3,1");
        aliveCells.add("2,2");
        GameBoard gameBoard = new GameBoard(3, aliveCells);
        gameBoard.calculateNextGeneration();
        List<List<Boolean>> board = gameBoard.getBoard();
        for (int i = 0; i < 3; ++i) {
            List<Boolean> row = board.get(i);
            for (int j = 0; j < 3; ++j) {
                assertTrue("Every cell in this generation should be alive, but we found " + (i + 1) + "," + (j + 1) + " to be dead", row.get(j));
            }
        }
        gameBoard.calculateNextGeneration();
        board = gameBoard.getBoard();
        for (int i = 0; i < 3; ++i) {
            List<Boolean> row = board.get(i);
            for (int j = 0; j < 3; ++j) {
                assertFalse("Every cell in this generation should be dead, but we found " + (i + 1) + "," + (j + 1) + " to be alive", row.get(j));
            }
        }
    }
}
