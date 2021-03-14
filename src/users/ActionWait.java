package users;

/**
 * Represents the "wait" action for a client's bidding strategy.
 */
public class ActionWait implements BiddingStrategy {

    /**
     * act method override from the BiddingStrategy interface.
     *
     * @param personalMax       The client's personal maximum possible bid.
     * @param lastPersonalBid   The client's last bid.
     * @param lastAuctionMaxBid The last bid on the last auction step
     *                          (accounting for all the clients in the auction).
     * @return The same last bid (client did not bid again basically).
     */
    @Override
    public double act(double personalMax, double lastPersonalBid,
                      double lastAuctionMaxBid) {
        return lastPersonalBid;
    }
}
