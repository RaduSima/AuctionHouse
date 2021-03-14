package exceptions;

/**
 * Checked exception for when a client is already logged and wants to log
 * again.
 */
public class DuplicateClientException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public DuplicateClientException(String message) {
        super(message);
    }
}
