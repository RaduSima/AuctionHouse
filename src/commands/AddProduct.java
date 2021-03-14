package commands;

import auctions.AuctionHouse;
import exceptions.DuplicateProductException;
import products.ProductFactory;

/**
 * Command that adds a product to the products list.
 */
public class AddProduct implements Command {

    /**
     * execute method override.
     *
     * @param auctionHouse     The auction house that the command will be executed
     *                         on.
     * @param parametersString The parameters of the command.
     */
    @Override
    public void execute(AuctionHouse auctionHouse, String parametersString) {
        try {
            auctionHouse.addProduct(
                    ProductFactory.getInstance().getProduct(parametersString));
        } catch (DuplicateProductException e) {
            e.printStackTrace();
        }
    }
}
