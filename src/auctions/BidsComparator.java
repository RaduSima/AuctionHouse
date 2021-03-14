package auctions;

import users.Client;

import java.util.Comparator;

/**
 * Comparator for bids.
 */
public class BidsComparator implements Comparator<Pair<Client, Double>> {

    /**
     * compare method override.
     * Compares 2 bids.
     *
     * @param o1 The first bid.
     * @param o2 The second bid.
     * @return Integer value representing the order of the bids.
     */
    @Override
    public int compare(Pair<Client, Double> o1, Pair<Client, Double> o2) {
        if (o1.getValue() < o2.getValue())
            return -1;
        else if (o1.getValue() > o2.getValue())
            return 1;
        else return o1.getKey().getNrAuctionsWon() -
                    o2.getKey().getNrAuctionsWon();
    }
}
