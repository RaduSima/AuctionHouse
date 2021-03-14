package users;

import auctions.Auction;
import auctions.Pair;
import products.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents a broker.
 */
public class Broker {

    /**
     * A list of pairs of clients that the broker is assigned to and their
     * respective auctions.
     */
    private final List<Pair<Auction, Client>> auctionClientsPairs;

    /**
     * The broker's unique personal number.
     */
    private final int personalNumber;

    /**
     * The broker's current total earnings, through the auction commissions.
     */
    private int currentEarnings;

    /**
     * The broker's currently ongoing auction.
     */
    private Auction currentAuction;

    /**
     * One parameter constructor.
     *
     * @param personalNumber The broker's unique personal number.
     */
    public Broker(int personalNumber) {
        auctionClientsPairs = Collections.synchronizedList(new ArrayList<>());
        this.personalNumber = personalNumber;
    }

    /**
     * Synchronized setter for current auction.
     *
     * @param currentAuction The current auction.
     */
    public synchronized void setCurrentAuction(Auction currentAuction) {
        this.currentAuction = currentAuction;
    }

    /**
     * Getter for the unique personal number.
     *
     * @return The unique personal number.
     */
    public int getPersonalNumber() {
        return personalNumber;
    }

    /**
     * Getter for current total earnings
     *
     * @return The total earnings.
     */
    public int getCurrentEarnings() {
        return currentEarnings;
    }

    /**
     * Adds a client-auction pair to the broker's list.
     *
     * @param auction The auction to be added.
     * @param client  The client to be added.
     */
    public void addClient(Auction auction, Client client) {
        auctionClientsPairs.add(new Pair<>(auction, client));
    }

    /**
     * Gets bids from all assigned clients.
     *
     * @param lastMaxBid Last maximum bid for the last auction step.
     * @return A list of pairs of bids and their respective clients.
     */
    public List<Pair<Client, Double>> getBids(double lastMaxBid) {
        ArrayList<Pair<Client, Double>> bids = new ArrayList<>();

        //Go through all the auctions in the list
        for (Pair<Auction, Client> currentPair : auctionClientsPairs) {
            Auction auct = currentPair.getKey();
            Client client = currentPair.getValue();

            //For each of them, get a new bid from the client.
            if (auct.equals(currentAuction)) {
                double bid = client.bid(currentAuction,
                        lastMaxBid);
                bids.add(new Pair<>(client, bid));
                System.out.println(
                        "Broker " + personalNumber + " got bid of " + bid +
                                " from client" +
                                " with ID " + client.getId() +
                                " and personal max of " +
                                client.getPersonalMax(currentAuction));
            }
        }

        return bids;
    }

    /**
     * Increase the number of total participations for all the assigned
     * clients that participated in a specific auction.
     *
     * @param finalisedAuction The finalised auction that the clients
     *                         participated at.
     */
    public void updateClientParticipation(Auction finalisedAuction) {
        for (Pair<Auction, Client> currentPair : auctionClientsPairs) {
            Auction auct = currentPair.getKey();
            Client client = currentPair.getValue();
            if (auct.equals(finalisedAuction)) {
                client.increaseNrParticipations();
            }
        }
    }

    /**
     * Gets commission from all assigned clients that participated in a
     * specific auction.
     *
     * @param auction The finalised auction that the clients participated at.
     */
    public void getPayment(Auction auction) {
        for (Pair<Auction, Client> pair : auctionClientsPairs) {
            if (pair.getKey().equals(auction)) {
                int payment = pair.getValue().payBroker();
                currentEarnings += payment;
                System.out.println(
                        "Broker " + personalNumber + " got paid " + payment +
                                " and now has " + currentEarnings);
            }
        }
    }

    /**
     * Removes the currently assigned auction from the map.
     */
    public synchronized void removeCurrentAuction() {

        //Removes max bids from clients
        for (Pair<Auction, Client> pair : auctionClientsPairs) {
            if (pair.getKey().equals(currentAuction)) {
                pair.getValue().removeMaxBid(currentAuction);
            }
        }

        //Removes pairs from map
        auctionClientsPairs
                .removeIf(pair -> pair.getKey().equals(currentAuction));
        currentAuction = null;
    }

    /**
     * Removes a product from an available list to a sold list.
     *
     * @param products     The available products list.
     * @param soldProducts The sold products list.
     * @param sold         The product to be sold.
     * @param winner       The client that bought the product over an auction.
     */
    public void removeProduct(List<Product> products, Map<Product,
            Client> soldProducts, Product sold, Client winner) {
        products.remove(sold);
        soldProducts.put(sold, winner);
    }

    /**
     * Checks if a certain client is assigned to this broker.
     *
     * @param client The client to be checked.
     * @return A boolean value representing if the broker is assigned to the
     * client or not.
     */
    public boolean hasClient(Client client) {
        for (Pair<Auction, Client> pair : auctionClientsPairs) {
            if (pair.getValue().equals(client))
                return true;
        }
        return false;
    }
}
