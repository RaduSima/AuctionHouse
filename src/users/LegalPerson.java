package users;

import java.util.Objects;

/**
 * Represents the legal person client type.
 */
public class LegalPerson extends Client {

    /**
     * The client's company type.
     */
    private final Company company;

    /**
     * The client's current share capital.
     */
    private final double shareCapital;

    /**
     * All parameter constructor.
     *
     * @param id           The client's unique id.
     * @param name         The client's name.
     * @param address      The client's address.
     * @param company      The client's company type.
     * @param shareCapital The client's share capital.
     */
    public LegalPerson(int id, String name, String address, Company company,
                       double shareCapital) {
        super(id, name, address);
        this.company = company;
        this.shareCapital = shareCapital;
    }

    /**
     * toString method override
     *
     * @return String representation of a legal person.
     */
    @Override
    public String toString() {
        return "LegalPerson{" +
                "company=" + company +
                ", shareCapital=" + shareCapital +
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LegalPerson that = (LegalPerson) o;
        return Double.compare(that.shareCapital,
                shareCapital) == 0 &&
                company == that.company;
    }

    /**
     * hashCode method override.
     *
     * @return The hash code of the client.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company, shareCapital);
    }

    /**
     * Calculates the broker commission for a legal person.
     *
     * @return The broker's commission.
     */
    @Override
    public int payBroker() {
        if (nrParticipations < 25)
            return (int) (25 * lastPersonalBid / 100);
        else
            return (int) (10 * lastPersonalBid / 100);
    }

    /**
     * Possible company types, represented as as enum.
     */
    public enum Company {
        SRL, SA
    }
}
