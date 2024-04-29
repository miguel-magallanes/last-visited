package com.dtoind.lastvisit.exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The LinkNotFoundException is thrown when a requested link cannot be found.
 * This is a runtime exception, indicating an unexpected condition in the application's logic.
 */
public class LinkNotFoundException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(LinkNotFoundException.class);

    /**
     * Constructs a new LinkNotFoundException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public LinkNotFoundException(String message) {
        super(message);
        logger.error("LinkNotFoundException occurred: {}", message);
    }

    /**
     * Constructs a new LinkNotFoundException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public LinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error("LinkNotFoundException occurred: {}", message, cause);
    }

}
