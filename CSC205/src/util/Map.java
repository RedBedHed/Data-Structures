package util;

public interface Map<K,V> extends EllieCollection {

    /**
     * A method to put a value into the {@code Map} with
     * the given key.
     *
     * @param k the key
     * @param v the value
     */
    void put(K k, V v);

    /**
     * A method to get a value using the corresponding key.
     *
     * @param k the key
     * @return the value stored with the given key.
     */
    V get(K k);

    /**
     * A method to remove a value using the corresponding key.
     *
     * @param k the key
     * @return the value stored with the given key.
     */
    V remove(K k);

}
