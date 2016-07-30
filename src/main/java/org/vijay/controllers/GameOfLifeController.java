package org.vijay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vijay.commons.BoardIndex;
import org.vijay.commons.ErrorCode;
import org.vijay.commons.InMemoryGameBoardStore;
import org.vijay.domains.GameBoard;
import org.vijay.exceptions.ValidationException;
import org.vijay.services.GameBoardService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Rest Controller for GameBoard
 * Exposes following urls:
 * GET - /board : list the boards
 * GET - /board/{id} : get the id with given board
 * POST - /board : create a new board
 * PUT - /board/{id} : calclulate next gen for the given board
 * DELETE - /board/{id} : delete the board with given id
 */
@RestController
@RequestMapping(value = "/board")
public class GameOfLifeController {
    @Autowired
    private GameBoardService gameBoardService;
    @Autowired
    private InMemoryGameBoardStore inMemoryGameBoardStore;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public GameBoard create(@RequestParam("size") Integer size, @RequestParam("indexes") String aliveIndexes) {
        GameBoard gameBoard = gameBoardService.createNew(size, convertFromStringToIndex(Arrays.asList(aliveIndexes.split("\\|")), size));
        gameBoard.setId(inMemoryGameBoardStore.getNextCounter());
        inMemoryGameBoardStore.getStore().put(gameBoard.getId(), gameBoard);
        return gameBoard;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "{id}")
    public GameBoard get(@PathVariable("id") Integer boardId) {
        return inMemoryGameBoardStore.getStore().get(boardId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<GameBoard> list() {
        return inMemoryGameBoardStore.getStore().values();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json", value = "{id}")
    public GameBoard updateToNextGen(@PathVariable("id") Integer boardId) {
        return gameBoardService.calculateNextGen(inMemoryGameBoardStore.getStore().get(boardId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable("id") Integer boardId) {
        if (inMemoryGameBoardStore.getStore().containsKey(boardId)) {
            inMemoryGameBoardStore.getStore().remove(boardId);
        } else {
            throw new ValidationException(ErrorCode.BOARD_NOT_FOUND, null);
        }
    }

    private List<BoardIndex> convertFromStringToIndex(List<String> indexes, Integer maxSize) {
        List<BoardIndex> boardIndexList = new ArrayList<>();
        indexes.forEach(index -> {
            String[] indexSplitList = index.split(",");
            if (indexSplitList.length != 2) {
                throw new ValidationException(ErrorCode.INVALID_INDEX, null);
            }
            try {
                int row = Integer.parseInt(indexSplitList[0]) - 1;
                int col = Integer.parseInt(indexSplitList[1]) - 1;
                boardIndexList.add(new BoardIndex(row, col, maxSize));
            } catch (NumberFormatException e) {
                throw new ValidationException(ErrorCode.INVALID_ROW_COL_NUMBER, e);
            }
        });
        return boardIndexList;
    }
}
