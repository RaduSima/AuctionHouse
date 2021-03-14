package users;

import java.util.Random;

/**
 * Represents the "bid" action for a client's bidding strategy.
 */
public class ActionBid implements BiddingStrategy {

    /**
     * act method override from the BiddingStrategy interface.
     *
     * @param personalMax       The client's personal maximum possible bid.
     * @param lastPersonalBid   The client's last bid.
     * @param lastAuctionMaxBid The last bid on the last auction step
     *                          (accounting for all the clients in the auction).
     * @return A new bid.
     */
    @Override
    public double act(double personalMax, double lastPersonalBid,
                      double lastAuctionMaxBid) {

        //Check if the client can bid more.
        if (lastAuctionMaxBid >= personalMax)
            //If not, return last bid.
            return lastPersonalBid;

        //If he can bid more, return a random bid between the last global
        // one and his personal max.
        return new Random().nextInt((int) (personalMax - lastAuctionMaxBid)) +
                lastAuctionMaxBid;
    }
}
