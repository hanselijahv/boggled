package exceptions;

/**
 *  Creates a throwable when a temp.game has less than 2 players.
 */
public class InsufficientPlayersException extends Exception {

    /**
     *
     *
     * @param message
     */
    public InsufficientPlayersException (String message) {
        super(message);
    }
}
