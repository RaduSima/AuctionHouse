package auctions;

import java.util.Objects;

/**
 * Represents an auction.
 */
public class Auction {

    /**
     * The id of the auction.
     */
    private final int id;

    /**
     * The maximum number of participants to an auction.
     */
    private final int nrParticipants;

    /**
     * The id of the product to be auctioned for.
     */
    private final int idProduct;

    /**
     * The maximum number of steps of the auction.
     */
    private final int nrStepsMax;

    /**
     * The current number of participants to the auction.
     */
    private int currentNrParticipants;

    /**
     * Four parameter constructor.
     *
     * @param id             The id of the auction.
     * @param nrParticipants The current number of participants.
     * @param idProduct      The id of the product to be auctioned for.
     * @param nrStepsMax     The maximum number of steps of the auction.
     */
    public Auction(int id, int nrParticipants, int idProduct, int nrStepsMax) {
        this.id = id;
        this.nrParticipants = nrParticipants;
        this.idProduct = idProduct;
        this.nrStepsMax = nrStepsMax;
        this.currentNrParticipants = 1;
    }

    /**
     * Synchronized getter for id.
     *
     * @return The id of the auction.
     */
    public synchronized int getId() {
        return id;
    }

    /**
     * Synchronized getter for the maximum number of participants.
     *
     * @return The maximum number of participants.
     */
    public synchronized int getNrParticipants() {
        return nrParticipants;
    }

    /**
     * Synchronized getter for the id of the auction's product.
     *
     * @return The id of the auction's product.
     */
    public synchronized int getIdProduct() {
        return idProduct;
    }

    /**
     * Synchronized getter for the maximum number of steps.
     *
     * @return The maximum number of steps.
     */
    public synchronized int getNrStepsMax() {
        return nrStepsMax;
    }

    /**
     * Synchronized getter for the current number of participants.
     *
     * @return The current number of participants.
     */
    public synchronized int getCurrentNrParticipants() {
        return currentNrParticipants;
    }

    /**
     * Increases the number of participants by 1.
     */
    public synchronized void increaseCurrentParticipants() {
        currentNrParticipants++;
    }

    /**
     * Gets the maximum and current number of participants, as a string.
     *
     * @return The participants ratio.
     */
    public String getParticipationRatio() {
        return getCurrentNrParticipants() + " / " + getNrParticipants() +
                " participants";
    }

    /**
     * equals method override.
     *
     * @param o The object to be compared.
     * @return A boolean value representing the equality between objects.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return id == auction.id;
    }

    /**
     * hashCode method override.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nrParticipants, idProduct, nrStepsMax);
    }
}
