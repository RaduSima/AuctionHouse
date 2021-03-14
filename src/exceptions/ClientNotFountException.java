package exceptions;

/**
 * Checked exception for when a requested client does not exist.
 */
public class ClientNotFountException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public ClientNotFountException(String message) {
        super(message);
    }
}
