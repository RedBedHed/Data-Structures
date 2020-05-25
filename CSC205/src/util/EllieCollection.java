package util;

/**
 * An interface for All {@code EllieCollection}s.
 * All {@code EllieCollection}s must implement the methods listed below.
 *
 * @author Ellie Moore
 * @version 1.0, 02-20-20
 */
public interface EllieCollection {

    /**
     * A method that allows the user access to the {@code size} of the {@code Collection}.
     *
     * @return the {@code size} of the {@code Deque}
     * @see java.util.Collection#size()
     */
    int size();

    /**
     * A method that returns a {@code boolean} value based on whether or not the
     * {@code Collection} is empty (i.e. its {@code size} is zero).
     *
     * @return whether or not the {@code Data Structure} is empty
     * @see java.util.Collection#isEmpty()
     */
    boolean isEmpty();

    /**
     * A method that describes the {@code Collection} using a {@code String}.
     * Overrides {@code Object}'s {@code toString()}.
     *
     * @return a {@code String} description of the {@code Collection}
     * @see Object#toString()
     */
    @Override
    String toString();

    /**
     * A method that describes the {@code Collection}'s {@code internal Storage}
     * using a {@code String}.
     *
     * @return a {@code String} description of the {@code internal} storage
     */
    String toStore();

    /**
     * This method overrides {@code Collection}'s {@code hashCode()} method. It
     * returns a unique integer based on the individual hashes of the Elements
     * in the {@code internal} storage.
     *
     * @return an {@code integer hash code}
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * This method overrides {@code Object}'s {@code equals(Object)} method. It
     * checks for equality between two {@code Collection}s by iterating through
     * each of their elements one-by-one and comparing them using their own
     * implementations of {@code Object#equals(Object)}.
     *
     * @param other the other {@code Object}
     * @return whether or not two {@code Collection}s are equal
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object other);

}
