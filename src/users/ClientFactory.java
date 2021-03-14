package users;

/**
 * Represents a client factory, for creating client objects.
 * Is a factory design pattern implementation.
 * Is a singleton class.
 */
public class ClientFactory {

    /**
     * The singleton instance.
     */
    private static ClientFactory instance;

    /**
     * Private constructor.
     */
    private ClientFactory() {

    }

    /**
     * Synchronized getter for the singleton instance.
     *
     * @return The singleton instance.
     */
    public static synchronized ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    /**
     * Creates a new product from a string of client information.
     *
     * @param clientInfo The string containing client information.
     * @return The newly created client.
     */
    public Client getClient(String clientInfo) {
        //Splits the string into parameters
        String[] info = clientInfo.split(" ");

        //Assigns general client fields
        int id = Integer.parseInt(info[1]);
        String name = info[2];
        String address = info[3];

        //Assigns specific fields, based on the client type (the first
        // parameter in the string)
        if (info[0].equalsIgnoreCase("NATURAL")) {
            String birthDate = info[4];
            return new NaturalPerson(id, name, address, birthDate);

        } else if (info[0].equalsIgnoreCase("LEGAL")) {
            LegalPerson.Company company = LegalPerson.Company.valueOf(info[4]);
            double shareCapital = Double.parseDouble(info[5]);
            return new LegalPerson(id, name, address, company, shareCapital);
        }
        return null;
    }
}
