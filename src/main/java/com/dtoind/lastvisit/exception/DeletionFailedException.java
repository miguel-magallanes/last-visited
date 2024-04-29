package com.dtoind.lastvisit.exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The DeletionFailedException is thrown when an attempt to delete an object fails.
 * This is a runtime exception, indicating an unexpected condition in the application's logic.
 */
public class DeletionFailedException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(DeletionFailedException.class);

    /**
     * Constructs a new DeletionFailedException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public DeletionFailedException(String message) {
        super(message);
        logger.error("DeletionFailedException occurred: {}", message);
    }

    /**
     * Constructs a new DeletionFailedException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public DeletionFailedException(String message, Throwable cause) {
        super(message, cause);
        logger.error("DeletionFailedException occurred: {}", message, cause);
    }

}
