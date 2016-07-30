package org.vijay.commons;

/**
 * Created by vijayt on 7/29/16.
 */
public enum ErrorCode {
    INVALID_SIZE_OR_INDEX(1, "invalid.size.or.alive.index"),
    INVALID_INDEX(2, "invalid.index"),
    INVALID_ROW_COL_NUMBER(3, "invalid.row.col.number"),
    BOARD_NOT_FOUND(4, "board.not.found");
    private final int number;
    private final String messageCode;

    ErrorCode(int number, String messageCode) {
        this.number = number;
        this.messageCode = messageCode;
    }

    public int getNumber() {
        return this.number;
    }

    public String getMessageCode() {
        return this.messageCode;
    }
}
