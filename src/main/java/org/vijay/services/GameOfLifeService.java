package org.vijay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vijay.domains.GameBoard;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vijayt on 7/24/2016.
 */
@RestController
public class GameOfLifeService {
    @Autowired
    private GameBoard gameBoard;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/config")
    public List<List<Boolean>> configure(@RequestParam("size") int size, @RequestParam("indexes") String aliveIndexes) {
        gameBoard = new GameBoard(size, Arrays.asList(aliveIndexes.split("\\|")));
        return gameBoard.getBoard();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/get-state")
    public List<List<Boolean>> getCurrentState() {
        return gameBoard.getBoard();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/calc-next-gen")
    public List<List<Boolean>> calculateNextGen() {
        gameBoard.calculateNextGeneration();
        return gameBoard.getBoard();
    }
}
