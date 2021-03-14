package products;

import java.util.Objects;

/**
 * Represents the painting product type.
 */
public class Painting extends Product {

    /**
     * The painter's name
     */
    private final String painterName;

    /**
     * The painting's color type.
     */
    private final Colors color;

    /**
     * All parameter constructor.
     *
     * @param id          The unique id.
     * @param year        The manufacturing year.
     * @param name        The product name.
     * @param minPrice    The minimum selling price.
     * @param painterName The painter's name.
     * @param color       The color type.
     */
    public Painting(int id, int year, String name, double minPrice,
                    String painterName,
                    Colors color) {
        super(id, year, name, minPrice);
        this.painterName = painterName;
        this.color = color;
    }

    /**
     * toString method override.
     *
     * @return The string representation of the painting.
     */
    @Override
    public String toString() {
        return "Painting{" +
                "painterName='" + painterName + '\'' +
                ", color=" + color +
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
        Painting painting = (Painting) o;
        return Objects.equals(painterName, painting.painterName) &&
                color == painting.color;
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the product.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), painterName, color);
    }

    /**
     * 3 possible color types, represented as an enum.
     */
    public enum Colors {
        OIL, TEMPERA, ACRYLIC
    }
}
