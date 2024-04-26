package net.team6.boggled.common.exceptions;

/**
 *  Creates a throwable when the user is not logged in.
 */
public class NotLoggedInException extends Exception {

    /**
     *
     * @param message error message.
     */
    public NotLoggedInException(String message) {
        super(message);
    }
}
