package users;

import auctions.Auction;

import java.util.*;

/**
 * Represents a client - can be legal or natural.
 */
public abstract class Client {

    /**
     * The unique id of the client.
     */
    protected final int id;

    /**
     * The name of the client.
     */
    protected final String name;

    /**
     * The address of the client.
     */
    protected final String address;
    /**
     * A map of auctions that the client is currently participating at and
     * respective maximum possible bids for them.
     */
    protected final Map<Auction, Integer> maxBids;
    /**
     * Total number of participations to auctions.
     */
    protected int nrParticipations;
    /**
     * Total number of auctions won.
     */
    protected int nrAuctionsWon;
    /**
     * The client's last bid.
     */
    protected double lastPersonalBid;

    /**
     * The way the client chooses to bid.
     */
    protected BiddingStrategy biddingStrategy;

    /**
     * 3 parameter constructor.
     *
     * @param id      The client's unique id.
     * @param name    The client's name.
     * @param address The client's address.
     */
    protected Client(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        maxBids = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Getter for id.
     *
     * @return The unique id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the total number of auctions won.
     *
     * @return The number of won auctions.
     */
    public int getNrAuctionsWon() {
        return nrAuctionsWon;
    }

    /**
     * Gets the client's maximum possible bid for a certain auction.
     *
     * @param auction The auction (as key in the map).
     * @return The client's maximum possible bid for the auction.
     */
    public int getPersonalMax(Auction auction) {
        return maxBids.get(auction);
    }

    /**
     * Places a bid for a current auction, based on the client's bidding
     * strategy and the last maximum bid on the last auction step.
     *
     * @param auction           The auction for which to bid.
     * @param lastAuctionMaxBid The maximum bid from the last auction step.
     * @return The client's new bid.
     */
    public double bid(Auction auction, double lastAuctionMaxBid) {

        //Randomly assigns a bidding strategy for this step ("to bid or not
        // to bid")
        if (new Random().nextBoolean())
            biddingStrategy = new ActionBid();
        else
            biddingStrategy = new ActionWait();

        //Bids
        lastPersonalBid = biddingStrategy.act(maxBids.get(auction),
                lastPersonalBid,
                lastAuctionMaxBid);
        return lastPersonalBid;
    }

    /**
     * Increases the total number of participations by 1.
     */
    public void increaseNrParticipations() {
        nrParticipations++;
    }

    /**
     * Increases the total number of won auctions by 1.
     */
    public void increaseNrWon() {
        nrAuctionsWon++;
    }

    /**
     * Assigns a maximum possible bid to a certain auction (in the map).
     *
     * @param auction The auction to be assigned a max possible bid.
     * @param maxBid  The maximum possbile bid.
     */
    public void assignMaxBid(Auction auction, int maxBid) {
        maxBids.put(auction, maxBid);
    }

    /**
     * Removes an auction-bid pair from the maximum possible bids map.
     *
     * @param auction The auction to be removed.
     */
    public void removeMaxBid(Auction auction) {
        maxBids.remove(auction);
        lastPersonalBid = 0;
    }

    /**
     * Abstract method for calculating broker commission.
     *
     * @return The broker's commission.
     */
    public abstract int payBroker();

    /**
     * toString method override
     *
     * @return String representation of a client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nrParticipation=" + nrParticipations +
                ", nrAuctionsWon=" + nrAuctionsWon +
                '}';
    }

    /**
     * equals method override.
     *
     * @param o The client to be compared to.
     * @return Boolean value representing equality between clients.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                nrParticipations == client.nrParticipations &&
                nrAuctionsWon == client.nrAuctionsWon &&
                name.equals(client.name) &&
                address.equals(client.address);
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the client.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}
