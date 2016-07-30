package org.vijay.commons;

/**
 * Simple class used to indicate location of a cell C in a given {@link org.vijay.domains.GameBoard}
 */
public class BoardIndex {
    private int row;
    private int column;

    public BoardIndex() {//needed by jackson
    }

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
