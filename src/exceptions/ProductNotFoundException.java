package exceptions;

/**
 * Checked exception for when a requested product already exists.
 */
public class ProductNotFoundException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
