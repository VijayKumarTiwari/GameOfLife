package org.vijay.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vijay.domains.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.stub;

/**
 * Created by vijayt on 7/24/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameOfLifeServiceTests {

    @InjectMocks
    private GameOfLifeService gameOfLifeService;
    @Mock
    private GameBoard gameBoard;

    @Test
    public final void testConfigure() {
        List expected = new ArrayList<>();
        stub(gameBoard.getBoard()).toReturn(expected);
        assertEquals(expected, gameOfLifeService.configure(0, ""));
    }

    @Test
    public final void testGetState() {
        List expected = new ArrayList<>();
        stub(gameBoard.getBoard()).toReturn(expected);
        assertEquals(expected, gameOfLifeService.getCurrentState());
    }

    @Test
    public final void testCalcNextGen() {
        List expected = new ArrayList<>();
        stub(gameBoard.getBoard()).toReturn(expected);
        assertEquals(expected, gameOfLifeService.calculateNextGen());
    }
}
