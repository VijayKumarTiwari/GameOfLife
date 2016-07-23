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

    public GameBoard(int size) {
        this.size = size;
        initBoard();
    }

    private void initBoard() {
        board = new ArrayList<List<Boolean>>();
        for (int i = 0; i < size; ++i) {
            ArrayList<Boolean> row = new ArrayList<Boolean>(Arrays.asList(new Boolean[size]));
            Collections.fill(row, Boolean.FALSE);
            board.add(row);
        }
    }

    public List<List<Boolean>> getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }
}
