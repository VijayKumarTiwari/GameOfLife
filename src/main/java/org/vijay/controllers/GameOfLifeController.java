package org.vijay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vijay.commons.BoardIndex;
import org.vijay.commons.InMemoryGameBoardStore;
import org.vijay.domains.GameBoard;
import org.vijay.services.GameBoardService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vijayt on 7/24/2016.
 */
@RestController
public class GameOfLifeController {
    @Autowired
    private GameBoardService gameBoardService;
    @Autowired
    private InMemoryGameBoardStore inMemoryGameBoardStore;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/config")
    public GameBoard configure(@RequestParam("size") Integer size, @RequestParam("indexes") String aliveIndexes) {
        GameBoard gameBoard = gameBoardService.createNew(size, convertFromStringToIndex(Arrays.asList(aliveIndexes.split("\\|")), size));
        gameBoard.setId(inMemoryGameBoardStore.getStore().size() + 1);
        inMemoryGameBoardStore.getStore().put(gameBoard.getId(), gameBoard);
        return gameBoard;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/get-state/{id}")
    public GameBoard getCurrentState(@PathVariable("id") Integer boardId) {
        return inMemoryGameBoardStore.getStore().get(boardId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/calc-next-gen/{id}")
    public GameBoard calculateNextGen(@PathVariable("id") Integer boardId) {
        return gameBoardService.calculateNextGen(inMemoryGameBoardStore.getStore().get(boardId));
    }

    private List<BoardIndex> convertFromStringToIndex(List<String> indexes, Integer maxSize) {
        List<BoardIndex> boardIndexList = new ArrayList<>();
        indexes.forEach(index -> {
            String[] indexSplitList = index.split(",");
            if (indexSplitList.length != 2) {
                throw new RuntimeException("invalid index " + index);
            }
            try {
                int row = Integer.parseInt(indexSplitList[0]) - 1;
                int col = Integer.parseInt(indexSplitList[1]) - 1;
                boardIndexList.add(new BoardIndex(row, col, maxSize));
            } catch (NumberFormatException e) {
                throw new RuntimeException("invalid number", e);
            }
        });
        return boardIndexList;
    }
}
