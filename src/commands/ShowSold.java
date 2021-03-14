package commands;

import auctions.AuctionHouse;

/**
 * Command that shows all sold products information.
 */
public class ShowSold implements Command {

    /**
     * execute method override.
     *
     * @param auctionHouse     The auction house that the command will be executed
     *                         on.
     * @param parametersString The parameters of the command.
     */
    @Override
    public void execute(AuctionHouse auctionHouse, String parametersString) {
        auctionHouse.showSoldProducts();
    }
}
