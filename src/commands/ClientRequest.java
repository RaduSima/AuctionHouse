package commands;

import auctions.AuctionHouse;
import exceptions.AuctionAlreadyActiveException;
import exceptions.ClientNotFountException;
import exceptions.ProductNotFoundException;

/**
 * Command that enrols a client to an auction.
 */
public class ClientRequest implements Command {

    /**
     * execute method override.
     *
     * @param auctionHouse     The auction house that the command will be executed
     *                         on.
     * @param parametersString The parameters of the command.
     */
    @Override
    public void execute(AuctionHouse auctionHouse, String parametersString) {
        String[] parameters = parametersString.split(" ");
        try {
            auctionHouse.processClientRequest(Integer.parseInt(parameters[0]),
                    Integer.parseInt(parameters[1]),
                    Integer.parseInt(parameters[2]));
        } catch (ClientNotFountException | ProductNotFoundException | AuctionAlreadyActiveException e) {
            e.printStackTrace();
        }
    }
}
