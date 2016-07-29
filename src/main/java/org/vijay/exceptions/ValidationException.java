package org.vijay.exceptions;

import org.vijay.commons.ErrorCode;

/**
 * Basic Exception Class with an error code and message
 */
public class ValidationException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;

    public ValidationException(ErrorCode error, Throwable cause) {
        super(error.getMessageCode(), cause);
        this.errorCode = error.getNumber();
        this.errorMessage = error.getMessageCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
