package auctions;

import exceptions.*;
import products.Product;
import products.ProductFactory;
import users.Broker;
import users.Client;
import users.ClientFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Represents an auction house.
 * Runnable singleton class.
 */
public class AuctionHouse implements Runnable {

    /**
     * The auction house singleton instance.
     */
    private static AuctionHouse instance;

    /**
     * A list of all available products.
     */
    private final List<Product> products;

    /**
     * A list of all logged on clients.
     */
    private final List<Client> clients;

    /**
     * A list of brokers.
     */
    private final List<Broker> brokers;

    /**
     * A list of currently active auctions.
     */
    private final List<Auction> activeAuctions;

    /**
     * A list of currently pending auctions.
     */
    private final List<Auction> pendingAuctions;

    /**
     * A map of sold products and their respective clients.
     */
    private final Map<Product, Client> soldProducts;

    /**
     * The currently active auction for the thread.
     */
    private Auction currentAuction;

    /**
     * Private one parameter constructor.
     *
     * @param brokersNumber The number of brokers.
     */
    private AuctionHouse(int brokersNumber) {

        //Initialises lists and map with their respective synchronised versions.
        products = Collections.synchronizedList(new ArrayList<>());
        clients = Collections.synchronizedList(new ArrayList<>());
        activeAuctions = Collections.synchronizedList(new ArrayList<>());
        pendingAuctions = Collections.synchronizedList(new ArrayList<>());
        brokers = Collections.synchronizedList(new ArrayList<>(brokersNumber));
        for (int i = 0; i < brokersNumber; i++) {
            brokers.add(new Broker(i));
        }
        soldProducts = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Synchronized getter for instance.
     *
     * @return The auction house singleton instance.
     */
    public static synchronized AuctionHouse getInstance() {
        if (instance == null) {
            instance = new AuctionHouse(new Random().nextInt(9) + 2);
        }
        return instance;
    }

    /**
     * Synchronized getter for the current auction.
     *
     * @return The current auction of the thread.
     */
    public synchronized Auction getCurrentAuction() {
        return currentAuction;
    }

    /**
     * Synchronized setter for the current auction.
     *
     * @param currentAuction The current auction to be set for the current
     *                       thread.
     */
    public synchronized void setCurrentAuction(Auction currentAuction) {
        this.currentAuction = currentAuction;
    }

    /**
     * Adds a new product to the products list.
     *
     * @param newProduct The new product to be added.
     * @throws DuplicateProductException When the product already exists.
     */
    public void addProduct(Product newProduct)
            throws DuplicateProductException {
        for (Product prod : products)
            if (newProduct.getId() == prod.getId()) {
                throw new DuplicateProductException(
                        "Product " + newProduct.toString() +
                                " already exists.");
            }
        products.add(newProduct);
    }

    /**
     * Reads and loads products from a file.
     *
     * @param filename The input filename.
     */
    public void loadProducts(String filename) {

        //Creates an input scanner
        Scanner productsScanner = null;
        try {
            productsScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Reads products
        while (productsScanner != null && productsScanner.hasNext()) {
            String productInfo = productsScanner.nextLine();
            try {
                addProduct(
                        ProductFactory.getInstance().getProduct(productInfo));
            } catch (DuplicateProductException e) {
                e.printStackTrace();
            }
        }

        //Closes the input scanner
        if (productsScanner != null) {
            productsScanner.close();
        }
    }

    /**
     * Adds a new client to the clients list.
     *
     * @param newClient The new client to be added.
     * @throws DuplicateClientException When the client already exists in the
     *                                  list.
     */
    public void addClient(Client newClient) throws DuplicateClientException {
        for (Client client : clients)
            if (newClient.getId() == client.getId()) {
                throw new DuplicateClientException(
                        "Client with ID " + newClient.getId() + " already " +
                                "exists.");
            }
        clients.add(newClient);
    }

    /**
     * Reads and adds clients from an input file to the clients list.
     *
     * @param filename The input filename.
     */
    public void logClients(String filename) {

        //Creates an input scanner
        Scanner clientScanner = null;
        try {
            clientScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Reads clients
        while (clientScanner != null && clientScanner.hasNext()) {
            String clientInfo = clientScanner.nextLine();
            try {
                addClient(ClientFactory.getInstance().getClient(clientInfo));
            } catch (DuplicateClientException e) {
                e.printStackTrace();
            }
        }

        //Closes the input scanner
        if (clientScanner != null) {
            clientScanner.close();
        }
    }

    /**
     * Updates a pending auction or creates a new one with a new client.
     *
     * @param clientID  The client id.
     * @param productID The product id.
     * @param maxBid    The maximum bid of the client.
     * @throws ClientNotFountException       When the client does not exist in the
     *                                       list.
     * @throws ProductNotFoundException      When the product does not exist in the
     *                                       list.
     * @throws AuctionAlreadyActiveException When the auction for that
     *                                       specific product is already active.
     */
    public void processClientRequest(int clientID, int productID, int maxBid)
            throws ClientNotFountException, ProductNotFoundException,
            AuctionAlreadyActiveException {
        Client client = checkClientRequest(clientID, productID);

        //Update pending auction
        for (Auction auction : pendingAuctions) {
            if (auction.getIdProduct() == productID) {
                updatePendingAuction(client, auction, maxBid);

                if (auction.getNrParticipants() ==
                        auction
                                .getCurrentNrParticipants()) {
                    startAuction(auction);
                }
                return;
            }
        }

        //Create new auction, if it was not pending
        createAuction(client, productID, maxBid);
    }

    /**
     * Shows all broker earnings.
     */
    public void showBrokerEarnings() {
        System.out.println("\nBroker earnings:");
        for (Broker broker : brokers) {
            System.out.println("Broker " + broker.getPersonalNumber() + ": " +
                    broker.getCurrentEarnings());
        }
    }

    /**
     * Shows all sold products information.
     */
    public void showSoldProducts() {
        System.out.println("\nSold products:");
        if (soldProducts.isEmpty()) {
            System.out.println("None");
            return;
        }
        for (Map.Entry<Product, Client> pair : soldProducts.entrySet()) {
            System.out.println(pair.getKey().toString() + " sold to client " +
                    "with id " + pair.getValue().getId());
        }
    }

    /**
     * Shows all available products information.
     */
    public void showProducts() {
        System.out.println("\nAvailable products:");
        if (products.isEmpty()) {
            System.out.println("None");
            return;
        }
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    /**
     * Shows all logged on client information.
     */
    public void showClients() {
        System.out.println("\nLogged clients:");
        if (clients.isEmpty()) {
            System.out.println("None");
            return;
        }
        for (Client client : clients) {
            System.out.println(client.toString());
        }
    }

    /**
     * Checks if a client request to join an auction for a specific product
     * is valid.
     *
     * @param clientID  The client id.
     * @param productID The product id.
     * @return The client that requested to join the auction, if the request
     * is valid, and NULL otherwise
     * @throws ClientNotFountException       When the client is not logged on.
     * @throws ProductNotFoundException      When the product does not exist.
     * @throws AuctionAlreadyActiveException When the auction is already active.
     */
    private Client checkClientRequest(int clientID, int productID)
            throws ClientNotFountException, ProductNotFoundException,
            AuctionAlreadyActiveException {

        //Find client
        Client client = null;
        for (Client currentClient : clients) {
            if (currentClient.getId() == clientID) {
                client = currentClient;
                break;
            }
        }
        if (client == null)
            throw new ClientNotFountException(
                    "Client with client ID " + clientID + " does not exist.");

        //Find product
        Product product = null;
        for (Product currentProduct : products) {
            if (currentProduct.getId() == productID) {
                product = currentProduct;
                break;
            }
        }
        if (product == null)
            throw new ProductNotFoundException(
                    "Product with product ID " + productID +
                            " does not exist.");

        //Check if auction is already ongoing
        for (Auction auction : activeAuctions) {
            if (auction.getIdProduct() == productID) {
                throw new AuctionAlreadyActiveException(
                        "Auction for product with " +
                                productID +
                                " is already active.");
            }
        }
        return client;
    }

    /**
     * Updates a pending auction with a new client.
     *
     * @param client  The client to be added to the auction.
     * @param auction The auction to be updated.
     * @param maxBid  The maximum bid of the client to be added.
     */
    private void updatePendingAuction(Client client, Auction auction,
                                      int maxBid) {
        auction.increaseCurrentParticipants();
        int clientID = client.getId();
        assignBroker(client, auction);
        client.assignMaxBid(auction, maxBid);
        System.out.println(
                "Added client with ID " + clientID +
                        " to auction with ID " +
                        auction.getId() + " - " +
                        auction.getParticipationRatio());
    }

    /**
     * Starts a pending auction.
     *
     * @param auction The pending auction to be started.
     */
    private void startAuction(Auction auction) {

        //Moves the auction from the pending list to the active list
        pendingAuctions.remove(auction);
        activeAuctions.add(auction);
        setCurrentAuction(auction);
        System.out.println("Started auction with ID " + auction.getId() + " -" +
                " " + auction.getParticipationRatio());

        //Creates a new thread for that auction.
        Thread auctionHouseThread = new Thread(this);
        auctionHouseThread.setPriority(Thread.MAX_PRIORITY);
        auctionHouseThread.start();

        try {
            auctionHouseThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Creates a new auction and adds it to the pending list.
     *
     * @param client    The first client of the auction.
     * @param productID The product id for the auction.
     * @param maxBid    The maximum bid of the first client.
     */
    private void createAuction(Client client, int productID, int maxBid) {
        Auction newAuction = new Auction(productID, 5,
                productID, 5);
        pendingAuctions.add(newAuction);
        assignBroker(client, newAuction);
        client.assignMaxBid(newAuction, maxBid);
        System.out.println();
        System.out.println(
                "Created auction with ID " + newAuction.getId() + " -" +
                        " " + newAuction.getParticipationRatio());
    }

    /**
     * Assigns a broker to a client in an auction.
     *
     * @param client  The client.
     * @param auction The broker.
     */
    private void assignBroker(Client client, Auction auction) {

        //Randomly chooses a broker from the list
        Broker chosenBroker =
                brokers.get(new Random().nextInt(brokers.size()));

        chosenBroker.addClient(auction, client);
    }

    /**
     * Gets the maximum bid (and its respective client) from a list of bids.
     *
     * @param bidsList The list of bids.
     * @param step     The current step of the auction that the bids were made for.
     * @return A pair representing the maximum bid and its respective client.
     */
    private Pair<Client, Double> getMaxBid(
            ArrayList<Pair<Client, Double>> bidsList,
            int step) {
        System.out.println("Auction house calculated max bid for " +
                "auction with id " + getCurrentAuction().getId() + ", step" +
                " " + step);

        return Collections.max(bidsList,
                new BidsComparator());
    }

    /**
     * Ends an active auction.
     *
     * @param possibleWinner The client who bid the most.
     * @param maxBid         The maximum bid for that auction.
     */
    private void finaliseAuction(Client possibleWinner, double maxBid) {

        //Update clients participation
        for (Broker currentBroker : brokers) {
            currentBroker.updateClientParticipation(currentAuction);
        }

        //Find product
        int currentProdID = currentAuction.getIdProduct();
        for (Product prod : products) {
            if (prod.getId() == currentProdID) {

                payBrokers(currentAuction);
                //If the minimum price has been reached, sell it
                if (maxBid >= prod.getMinPrice()) {
                    sellProduct(possibleWinner, prod, maxBid);
                } else {
                    System.out.println(
                            "Did not sell product with ID " + currentProdID +
                                    " and min price " + prod.getMinPrice());
                }
                break;
            }
        }

        //Remove auction
        for (Broker currentBroker : brokers) {
            currentBroker.removeCurrentAuction();
        }
        activeAuctions.remove(currentAuction);
        synchronized (this) {
            currentAuction = null;
        }
    }

    /**
     * Sells a product.
     *
     * @param winner The client that bought the product over an auction.
     * @param prod   The product to be sold.
     * @param bid    The bid for which the product was sold.
     */
    private void sellProduct(Client winner, Product prod,
                             double bid) {

        winner.increaseNrWon();
        System.out.println("Sold product with ID " + prod.getId() + " and min" +
                " price of " + prod.getMinPrice() + " to " +
                "client with ID " + winner.getId() + " for " + bid);

        //Remove sold product from list
        prod.setSellingPrice(bid);
        for (Broker broker : brokers) {
            if (broker.hasClient(winner)) {
                broker.removeProduct(products, soldProducts, prod, winner);
                break;
            }
        }
        products.remove(prod);
    }

    /**
     * Pays the brokers their commission.
     *
     * @param auction The auction for which the brokers are getting paid.
     */
    private void payBrokers(Auction auction) {
        for (Broker broker : brokers) {
            broker.getPayment(auction);
        }
    }

    /**
     * run method override from the Runnable interface.
     * Runs an auction.
     */
    @Override
    public void run() {

        //Sets the brokers' current auction
        for (Broker currentBroker : brokers) {
            currentBroker.setCurrentAuction(currentAuction);
        }

        //Gets bids from brokers for every step
        Pair<Client, Double> maxBidPair = new Pair<>(null, 0d);
        ArrayList<Pair<Client, Double>> bidsList;
        for (int i = 0; i < getCurrentAuction().getNrStepsMax(); i++) {
            bidsList = new ArrayList<>();
            for (Broker currentBroker : brokers) {
                if (maxBidPair != null) {
                    bidsList.addAll(
                            currentBroker.getBids(maxBidPair.getValue()));
                }
            }

            //Calculates the maximum bid
            System.out.println("Bids are: " + bidsList);
            maxBidPair = getMaxBid(bidsList, i);
            System.out.println("Max bid is: " + maxBidPair);
        }

        //Ends the auction
        if (maxBidPair != null) {
            finaliseAuction(maxBidPair.getKey(), maxBidPair.getValue());
        }
    }
}
