package products;

/**
 * Represents a product factory, for creating product objects.
 * Is a factory design pattern implementation.
 * Is a singleton class.
 */
public class ProductFactory {

    /**
     * The singleton instance.
     */
    private static ProductFactory instance;

    /**
     * Private constructor.
     */
    private ProductFactory() {

    }

    /**
     * Synchronized getter for the singleton instance.
     *
     * @return The singleton instance.
     */
    public static synchronized ProductFactory getInstance() {
        if (instance == null) {
            instance = new ProductFactory();
        }
        return instance;
    }

    /**
     * Creates a new product from a string of product information.
     *
     * @param productInfo The string containing product information.
     * @return The newly created product.
     */
    public Product getProduct(String productInfo) {

        //Splits the string into parameters
        String[] info = productInfo.split(" ");

        //Assigns general product fields
        int id = Integer.parseInt(info[1]);
        int year = Integer.parseInt(info[2]);
        String name = info[3];
        double minPrice = Double.parseDouble(info[4]);

        //Assigns specific fields, based on the product type (the first
        // parameter in the string)
        if (info[0].equalsIgnoreCase("PAINTING")) {
            String painterName = info[5];
            Painting.Colors color = Painting.Colors.valueOf(info[6]);
            return new Painting(id, year, name, minPrice, painterName, color);

        } else if (info[0].equalsIgnoreCase("FURNITURE")) {
            String type = info[5];
            String material = info[6];
            return new Furniture(id, year, name, minPrice, type, material);

        } else if (info[0].equalsIgnoreCase("JEWELRY")) {
            String material = info[5];
            boolean isPrecious = false;
            if (info[6].equalsIgnoreCase("ISPRECIOUS")) {
                isPrecious = true;
            }

            return new Jewelry(id, year, name, minPrice, material, isPrecious);
        }
        return null;
    }
}