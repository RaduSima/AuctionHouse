package exceptions;

/**
 * Checked exception for when a given command does not exist.
 */
public class CommandNotFoundException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public CommandNotFoundException(String message) {
        super(message);
    }
}
