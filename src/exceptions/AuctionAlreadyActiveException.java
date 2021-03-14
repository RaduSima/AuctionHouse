package exceptions;

/**
 * Checked exception for when a client wants to join an auction but it is
 * already ongoing.
 */
public class AuctionAlreadyActiveException extends Exception {

    /**
     * One parameter constructor.
     *
     * @param message The error log.
     */
    public AuctionAlreadyActiveException(String message) {
        super(message);
    }
}
