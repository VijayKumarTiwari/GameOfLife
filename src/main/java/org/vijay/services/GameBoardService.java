package org.vijay.services;

import org.springframework.stereotype.Service;
import org.vijay.commons.BoardIndex;
import org.vijay.domains.GameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for {@link GameBoard}
 */
@Service
public class GameBoardService {

    /**
     * Used to initialize a new {@link GameBoard}
     * @param size
     * @param initialActiveIndexList
     * @return
     */
    public GameBoard createNew(int size, List<BoardIndex> initialActiveIndexList) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.initBoard(size, initialActiveIndexList);
        return gameBoard;
    }

    /**
     * Calculate next generation of the {@link GameBoard}
     * Algorithm Used: At any cell C calculate N the sum of all its alive neighbours and itself
     * If N is 3 : The cell C will be alive in next gen
     * Else : The cell C will be dead
     * Also instead of keeping a list of cells to keep alive/dead in next gen we keep a list to indicate which cells must be toggled from alive-to-dead and vice-versa
     * @param gameBoard
     * @return
     */
    public GameBoard calculateNextGen(GameBoard gameBoard) {
        List<BoardIndex> nextGenerationToggleIndexes = new ArrayList<>();
        for (int i = 0; i < gameBoard.getSize(); ++i) {
            List<Boolean> currentRow = gameBoard.getBoard().get(i);
            List<Boolean> aboveRow = i == 0 ? gameBoard.getBoard().get(gameBoard.getSize() - 1) : gameBoard.getBoard().get(i - 1);
            List<Boolean> belowRow = i == gameBoard.getSize() - 1 ? gameBoard.getBoard().get(0) : gameBoard.getBoard().get(i + 1);
            for (int j = 0; j < gameBoard.getSize(); ++j) {
                int nextColIndex = j == gameBoard.getSize() - 1 ? 0 : j + 1;
                int prevColIndex = j == 0 ? gameBoard.getSize() - 1 : j - 1;
                int aliveCount = calcAliveNeighbourCount(currentRow, aboveRow, belowRow, j, nextColIndex, prevColIndex);
                //check and set next generation value
                if (aliveCount == 3 && !currentRow.get(j)) {
                    nextGenerationToggleIndexes.add(new BoardIndex(i, j, gameBoard.getSize()));
                } else if (aliveCount != 3 && currentRow.get(j)) {
                    nextGenerationToggleIndexes.add(new BoardIndex(i, j, gameBoard.getSize()));
                }
            }
        }
        if (nextGenerationToggleIndexes.size() > 0 || gameBoard.isAliveCellsPresent()) {
            setNextGeneration(gameBoard, nextGenerationToggleIndexes);
        }
        return gameBoard;
    }

    private int calcAliveNeighbourCount(List<Boolean> currentRow, List<Boolean> aboveRow, List<Boolean> belowRow,
                                        int currentIndex, int nextIndex, int prevIndex) {
        int aliveCount = 0;
        //north
        if (aboveRow.get(currentIndex)) {
            aliveCount++;
        }
        //current cell
        if (currentRow.get(currentIndex)) {
            aliveCount++;
        }
        //south
        if (belowRow.get(currentIndex)) {
            aliveCount++;
        }

        //north-east
        if (aboveRow.get(nextIndex)) {
            aliveCount++;
        }
        //east
        if (currentRow.get(nextIndex)) {
            aliveCount++;
        }
        //south-east
        if (belowRow.get(nextIndex)) {
            aliveCount++;
        }

        //north-west
        if (aboveRow.get(prevIndex)) {
            aliveCount++;
        }
        //west
        if (currentRow.get(prevIndex)) {
            aliveCount++;
        }
        //south-west
        if (belowRow.get(prevIndex)) {
            aliveCount++;
        }

        return aliveCount;
    }

    /**
     * Iterate over the given indexes and set the value=!value
     *
     * @param gameBoard
     * @param nextGenerationToggleIndex
     */
    private void setNextGeneration(GameBoard gameBoard, List<BoardIndex> nextGenerationToggleIndex) {
        nextGenerationToggleIndex.forEach(boardIndex -> {
            gameBoard
                    .getBoard()
                    .get(boardIndex.getRow())
                    .set(
                            boardIndex.getColumn(),
                            !gameBoard
                                    .getBoard()
                                    .get(boardIndex.getRow())
                                    .get(boardIndex.getColumn()));
        });
    }

}
