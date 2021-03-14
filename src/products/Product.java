package products;

import java.util.Objects;

/**
 * Represents a product
 */
public abstract class Product {

    /**
     * The product's unique id.
     */
    protected final int id;

    /**
     * The year in which the product was manufactured.
     */
    protected final int year;

    /**
     * The name of the product.
     */
    protected final String name;

    /**
     * The minimum selling price of the product.
     */
    protected final double minPrice;

    /**
     * The price for which the product was last sold in an auction.
     */
    protected double sellingPrice;

    /**
     * 4 parameter constructor.
     *
     * @param id       The unique id.
     * @param year     The manufacturing year.
     * @param name     The product name.
     * @param minPrice The minimum selling price.
     */
    protected Product(int id, int year, String name,
                      double minPrice) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.minPrice = minPrice;
    }

    /**
     * Getter for id.
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for last selling price in an auction.
     *
     * @param sellingPrice The last selling price.
     */
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter for minimum selling price.
     *
     * @return The minimum selling price.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * toString method override
     *
     * @return String representation of a product.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", minPrice=" + minPrice +
                '}';
    }

    /**
     * equals method override.
     *
     * @param o The product to be compared to.
     * @return Boolean value representing equality between products (it
     * is based on id).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, year, name, minPrice);
    }
}
