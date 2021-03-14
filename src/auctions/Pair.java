package auctions;

/**
 * Generic pair of 2 objects.
 *
 * @param <K> The first object.
 * @param <V> The second object.
 */
public class Pair<K, V> {

    /**
     * The first object.
     */
    private final K key;

    /**
     * The second object.
     */
    private final V value;

    /**
     * 2 parameter constructor.
     *
     * @param key   The first object.
     * @param value The second object.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Synchronised getter for the first object in the pair.
     *
     * @return The first object in the pair.
     */
    public synchronized K getKey() {
        return key;
    }

    /**
     * Synchronised getter for the second object in the pair.
     *
     * @return The second object in the pair.
     */
    public synchronized V getValue() {
        return value;
    }

    /**
     * toString method override.
     *
     * @return The string representation of the pair (only the second object).
     */
    @Override
    public String toString() {
        return value + "";
    }
}
