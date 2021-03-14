package commands;

import auctions.AuctionHouse;

/**
 * Command that reads product information from a file and saves it into the
 * auction house.
 */
public class LoadProducts implements Command {

    /**
     * execute method override.
     *
     * @param auctionHouse     The auction house that the command will be executed
     *                         on.
     * @param parametersString The parameters of the command.
     */
    @Override
    public void execute(AuctionHouse auctionHouse, String parametersString) {
        auctionHouse.loadProducts(parametersString);
    }
}
