package org.vijay.commons;

/**
 * Created by vijayt on 7/28/16.
 */
public class BoardIndex {
    private int row;
    private int column;

    public BoardIndex(int row, int column, int boardSize) {
        if (row < 0 || column < 0 || row >= boardSize || column >= boardSize) {
            throw new RuntimeException("invalid board index");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
