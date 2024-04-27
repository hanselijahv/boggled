package net.team6.boggled.common.exceptions;

/**
 *  Creates a throwable when a user is not found in the database.
 */
public class UserNotFoundException extends Exception {

    /**
     *
     * @param message error message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
