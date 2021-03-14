package products;

import java.util.Objects;

/**
 * Represents the jewelry product type.
 */
public class Jewelry extends Product {

    /**
     * The material of the jewelry.
     */
    private final String material;

    /**
     * Boolean field representing if the jewelry is precious or not.
     */
    private final boolean isPrecious;

    /**
     * All parameter constructor.
     *
     * @param id         The unique id.
     * @param year       The manufacturing year.
     * @param name       The product name.
     * @param minPrice   The minimum selling price.
     * @param material   The furniture material.
     * @param isPrecious Boolean value representing if the jewelry is
     *                   precious or not.
     */
    public Jewelry(int id, int year, String name, double minPrice,
                   String material, boolean isPrecious) {
        super(id, year, name, minPrice);
        this.material = material;
        this.isPrecious = isPrecious;
    }

    /**
     * toString method override.
     *
     * @return The string representation of the jewelry.
     */
    @Override
    public String toString() {
        return "Jewelry{" +
                "material='" + material + '\'' +
                ", isPrecious=" + isPrecious +
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
        Jewelry jewelry = (Jewelry) o;
        return isPrecious == jewelry.isPrecious &&
                Objects.equals(material, jewelry.material);
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), material, isPrecious);
    }
}
