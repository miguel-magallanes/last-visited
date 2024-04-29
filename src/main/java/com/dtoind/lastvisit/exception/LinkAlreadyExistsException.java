package com.dtoind.lastvisit.exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The LinkAlreadyExistsException is thrown when an attempt is made to create a link that already exists.
 * This is a runtime exception, indicating an unexpected condition in the application's logic.
 */
public class LinkAlreadyExistsException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(LinkAlreadyExistsException.class);

    /**
     * Constructs a new LinkAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public LinkAlreadyExistsException(String message) {
        super(message);
        logger.error("LinkAlreadyExistsException occurred: {}", message);
    }

    /**
     * Constructs a new LinkAlreadyExistsException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public LinkAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
        logger.error("LinkAlreadyExistsException occurred: {}", message, cause);
    }

}

