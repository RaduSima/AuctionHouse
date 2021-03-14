package commands;

import auctions.AuctionHouse;

/**
 * Represents a command.
 * Is a command design pattern implementation.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param auctionHouse     The auction house that the command will be executed
     *                         on.
     * @param parametersString The parameters of the command.
     */
    void execute(AuctionHouse auctionHouse,
                 String parametersString);
}
