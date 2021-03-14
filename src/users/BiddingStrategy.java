package users;

/**
 * Represents a client's bidding strategy.
 * Is a factory strategy pattern implementation.
 */
public interface BiddingStrategy {

    /**
     * A client's way of acting on auction steps.
     *
     * @param personalMax       The client's personal maximum possible bid.
     * @param lastPersonalBid   The client's last bid.
     * @param lastAuctionMaxBid The last bid on the last auction step
     *                          (accounting for all the clients in the auction).
     * @return A new bid, or the last one (unchanged).
     */
    double act(double personalMax, double lastPersonalBid,
               double lastAuctionMaxBid);
}
