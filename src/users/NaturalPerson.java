package users;

import java.util.Objects;

/**
 * Represents the natural person client type.
 */
public class NaturalPerson extends Client {

    /**
     * The client's date of birth.
     */
    private final String birthDate;

    /**
     * All parameter constructor.
     *
     * @param id        The client's unique id.
     * @param name      The client's name.
     * @param address   The client's address.
     * @param birthDate The client's birth date.
     */
    public NaturalPerson(int id, String name, String address,
                         String birthDate) {
        super(id, name, address);
        this.birthDate = birthDate;
    }

    /**
     * toString method override
     *
     * @return String representation of a legal person.
     */
    @Override
    public String toString() {
        return "NaturalPerson{" +
                "birthDate='" + birthDate + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * equals method override.
     *
     * @param o The client to be compared to.
     * @return Boolean value representing equality between clients.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the client.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthDate);
    }

    /**
     * Calculates the broker commission for a natural person.
     *
     * @return The broker's commission.
     */
    @Override
    public int payBroker() {
        if (nrParticipations < 5)
            return (int) (20 * lastPersonalBid / 100);
        else
            return (int) (15 * lastPersonalBid / 100);
    }
}
