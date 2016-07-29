package org.vijay.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * Created by vijayt on 7/29/16.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GameOfLifeExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GameOfLifeExceptionHandler.class);
    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException ve) {
        logger.error(ve.getErrorMessage(), ve);
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(ve.getErrorMessage(), null, locale);
    }
}
