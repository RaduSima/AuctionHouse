package exceptions;

/**
 * Checked exception for a product to be loaded already exists.
 */
public class DuplicateProductException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public DuplicateProductException(String message) {
        super(message);
    }
}
