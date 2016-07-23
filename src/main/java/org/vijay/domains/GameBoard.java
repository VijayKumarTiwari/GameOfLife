package org.vijay.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by vijayt on 7/23/2016.
 */
public class GameBoard {
    int size;
    List<List<Boolean>> board;

    //Assumption the indexes are comma separated
    //i.e each element in the list would be like 1,2 indicating that the cell at row 1 and col 2 is alive
    //in list terms index [0][1] is alive
    public GameBoard(int size, List<String> initalAliveIndexes) {
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
        initalAliveIndexes.forEach(indexString -> {
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
    }

    public List<List<Boolean>> getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public void calculateNextGeneration(){
        
    }

}
