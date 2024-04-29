package com.dtoind.lastvisit.exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The CategoryNotFoundException is thrown when a requested category cannot be found.
 * This is a runtime exception, indicating an unexpected condition in the application's logic.
 */
public class CategoryNotFoundException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(CategoryNotFoundException.class);

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public CategoryNotFoundException(String message) {
        super(message);
        logger.error("CategoryNotFoundException occurred: {}", message);
    }

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error("CategoryNotFoundException occurred: {}", message, cause);
    }

}
