package products;

import java.util.Objects;

/**
 * Represents the furniture product type.
 */
public class Furniture extends Product {

    /**
     * The type of furniture.
     */
    private final String type;

    /**
     * The material of the furniture.
     */
    private final String material;

    /**
     * All parameter constructor.
     *
     * @param id       The unique id.
     * @param year     The manufacturing year.
     * @param name     The product name.
     * @param minPrice The minimum selling price.
     * @param type     The furniture type.
     * @param material The furniture material.
     */
    public Furniture(int id, int year, String name, double minPrice,
                     String type, String material) {
        super(id, year, name, minPrice);
        this.type = type;
        this.material = material;
    }

    /**
     * toString method override.
     *
     * @return The string representation of the furniture.
     */
    @Override
    public String toString() {
        return "Furniture{" +
                "type='" + type + '\'' +
                ", material='" + material + '\'' +
                ", id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", minPrice=" + minPrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }

    /**
     * equals method override.
     *
     * @param o The product to be compared to.
     * @return Boolean value representing equality between products.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Furniture furniture = (Furniture) o;
        return Objects.equals(type, furniture.type) &&
                Objects.equals(material, furniture.material);
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, material);
    }
}
