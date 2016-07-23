package org.vijay.domains;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by vijayt on 7/23/2016.
 */
@Component
public class GameBoard {
    int size;
    boolean aliveCellsPresent;
    List<List<Boolean>> board;

    public GameBoard(){
        //required by the spring autowire
    }

    //Assumption the indexes are comma separated
    //i.e each element in the list would be like 1,2 indicating that the cell at row 1 and col 2 is alive
    //in list terms index [0][1] is alive
    public GameBoard(int size, List<String> initalAliveIndexes) {
        if (size < 3 || initalAliveIndexes == null) {
            throw new RuntimeException("Invalid board size or alive indexes");
        }
        this.size = size;
        initBoard(initalAliveIndexes);
    }

    private void initBoard(List<String> initalAliveIndexes) {
        board = new ArrayList<List<Boolean>>();
        for (int i = 0; i < size; ++i) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(Arrays.asList(new Boolean[size]));
            Collections.fill(row, Boolean.FALSE);
            board.add(row);
        }

        //set the alive cell to Boolean.TRUE
        setAliveCells(initalAliveIndexes);
    }

    private void resetBoard() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board.get(i).set(j, Boolean.FALSE);
            }
        }
    }

    private void setAliveCells(List<String> aliveIndexes) {
        aliveIndexes.forEach(indexString -> {
            String[] indexes = indexString.split(",");
            if (indexes.length != 2) {
                throw new RuntimeException("Invalid index provided");
            }
            int row = Integer.parseInt(indexes[0]);
            int col = Integer.parseInt(indexes[1]);
            if (row > size || col > size || row < 1 || col < 1) {
                throw new RuntimeException("Invalid row/col number");
            }
            board.get(row - 1).set(col - 1, Boolean.TRUE);
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

    public int getSize() {
        return size;
    }

    public void calculateNextGeneration() {
        List<String> nextGenerationAliveIndexes = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            List<Boolean> currentRow = board.get(i);
            List<Boolean> aboveRow = i == 0 ? board.get(size - 1) : board.get(i - 1);
            List<Boolean> belowRow = i == size - 1 ? board.get(0) : board.get(i + 1);
            for (int j = 0; j < size; ++j) {
                int aliveCount = 0;
                //north
                if (aboveRow.get(j)) {
                    aliveCount++;
                }
                //current cell
                if (currentRow.get(j)) {
                    aliveCount++;
                }
                //south
                if (belowRow.get(j)) {
                    aliveCount++;
                }
                int nextColIndex = j == size - 1 ? 0 : j + 1;
                //north-east
                if (aboveRow.get(nextColIndex)) {
                    aliveCount++;
                }
                //east
                if (currentRow.get(nextColIndex)) {
                    aliveCount++;
                }
                //south-east
                if (belowRow.get(nextColIndex)) {
                    aliveCount++;
                }
                int prevColIndex = j == 0 ? size - 1 : j - 1;
                //north-west
                if (aboveRow.get(prevColIndex)) {
                    aliveCount++;
                }
                //west
                if (currentRow.get(prevColIndex)) {
                    aliveCount++;
                }
                //south-west
                if (belowRow.get(prevColIndex)) {
                    aliveCount++;
                }
                //check and set next generation value
                if (aliveCount == 3) {
                    nextGenerationAliveIndexes.add((i + 1) + "," + (j + 1));
                }
            }
        }
        if (nextGenerationAliveIndexes.size() > 0 || aliveCellsPresent) {
            setNextGeneration(nextGenerationAliveIndexes);
        }
    }

    private void setNextGeneration(List<String> aliveIndexes) {
        resetBoard();
        setAliveCells(aliveIndexes);
    }
}
