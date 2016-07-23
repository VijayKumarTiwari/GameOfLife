package org.vijay.domains;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * Created by vijayt on 7/23/2016.
 */
public class GameBoardTest {
    @Test
    public final void testNewBoard() {
        GameBoard gameBoard = new GameBoard(3);
        List<List<Boolean>> board = gameBoard.board;
        board.forEach(list -> {
            list.forEach(cell -> {
                assertFalse("Every cell must be initialized with false inidcating dead cell", cell);
            });
        });
    }
}
