package org.vijay.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vijay.commons.InMemoryGameBoardStore;
import org.vijay.controllers.GameOfLifeController;
import org.vijay.domains.GameBoard;
import org.vijay.services.GameBoardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.stub;

/**
 * Created by vijayt on 7/24/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameOfLifeControllerTests {

    @InjectMocks
    private GameOfLifeController gameOfLifeController;
    @Mock
    private GameBoardService gameBoardService;
    @Mock
    private InMemoryGameBoardStore inMemoryGameBoardStore;

    @Test
    public final void testConfigure() {
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.configure(3, "1,2"));
        assertEquals(1, gameBoard.getId().intValue());
    }

    @Test
    public final void testGetState() {
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.configure(3, "1,2"));
        assertEquals(gameBoard, gameOfLifeController.getCurrentState(1));
    }

    @Test
    public final void testCalcNextGen() {
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.configure(3, "1,2"));
        stub(gameBoardService.calculateNextGen(any(GameBoard.class))).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.calculateNextGen(1));
    }
}
