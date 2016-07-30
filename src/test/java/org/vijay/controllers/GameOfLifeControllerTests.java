package org.vijay.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vijay.commons.InMemoryGameBoardStore;
import org.vijay.domains.GameBoard;
import org.vijay.exceptions.ValidationException;
import org.vijay.services.GameBoardService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    public final void testCreate() {
        int i=0;
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        stub(inMemoryGameBoardStore.getNextCounter()).toReturn(++i);
        assertEquals(gameBoard, gameOfLifeController.create(3, "1,2"));
        assertEquals(1, gameBoard.getId().intValue());
    }

    @Test
    public final void testGet() {
        int i=0;
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        stub(inMemoryGameBoardStore.getNextCounter()).toReturn(++i);
        assertEquals(gameBoard, gameOfLifeController.create(3, "1,2"));
        assertEquals(gameBoard, gameOfLifeController.get(1));
    }

    @Test
    public final void testUpdateToNextGen() {
        GameBoard gameBoard = new GameBoard();
        stub(inMemoryGameBoardStore.getStore()).toReturn(new HashMap<>());
        stub(gameBoardService.createNew(anyInt(), anyList())).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.create(3, "1,2"));
        stub(gameBoardService.calculateNextGen(any(GameBoard.class))).toReturn(gameBoard);
        assertEquals(gameBoard, gameOfLifeController.updateToNextGen(1));
    }

    @Test
    public final void testList() {
        GameBoard gameBoard = new GameBoard();
        Map hashMap = new HashMap<>();
        hashMap.put(1, gameBoard);
        stub(inMemoryGameBoardStore.getStore()).toReturn(hashMap);
        assertTrue(gameOfLifeController.list().contains(gameBoard));
    }

    @Test(expected = ValidationException.class)
    public final void testDelete() {
        GameBoard gameBoard = new GameBoard();
        Map hashMap = new HashMap<>();
        hashMap.put(1, gameBoard);
        stub(inMemoryGameBoardStore.getStore()).toReturn(hashMap);
        gameOfLifeController.delete(1);
        assertNull(hashMap.get(1));
        gameOfLifeController.delete(1);
    }
}
