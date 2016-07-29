package org.vijay.domains;

import org.springframework.stereotype.Component;
import org.vijay.commons.BoardIndex;
import org.vijay.commons.ErrorCode;
import org.vijay.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * GameBoard class, represents a nXn board.
 * size: represents n.
 * aliveCellsPresent: flag is used to identify if any cells are alive, it is used while setting the board values
 * board: represents the actual game board
 */
@Component
public class GameBoard {
    Integer id;
    Integer size;
    Boolean aliveCellsPresent;
    List<List<Boolean>> board;

    public void initBoard(int size, List<BoardIndex> initalAliveIndexes) {
        if (size < 3 || initalAliveIndexes == null) {
            throw new ValidationException(ErrorCode.INVALID_SIZE_OR_INDEX, null);
        }
        this.size = size;
        board = new ArrayList<List<Boolean>>();
        for (int i = 0; i < size; ++i) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(Arrays.asList(new Boolean[size]));
            Collections.fill(row, Boolean.FALSE);
            board.add(row);
        }

        //set the alive cell to Boolean.TRUE
        setAliveCells(initalAliveIndexes);
    }

    private void setAliveCells(List<BoardIndex> aliveIndexes) {
        aliveIndexes.forEach(boardIndex -> {
            board.get(boardIndex.getRow()).set(boardIndex.getColumn(), Boolean.TRUE);
        });
        if (aliveIndexes.size() > 0) {
            aliveCellsPresent = true;
        } else {
            aliveCellsPresent = false;
        }
    }

    public List<List<Boolean>> getBoard() {
        return board;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean isAliveCellsPresent() {
        return aliveCellsPresent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameBoard gameBoard = (GameBoard) o;

        if (getId() != gameBoard.getId()) return false;
        return getSize() == gameBoard.getSize();

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getSize();
        return result;
    }
}
