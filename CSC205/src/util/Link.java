package util;

/**
 * Link
 *
 * <p>
 * The basic unit of a Linked Data Structure.
 *
 * @param <E> the type
 */
public final class Link<E> {

    /**
     * A variable to store the {@code Links}'s data.
     */
    protected E store;

    /**
     * A reference to the next {@code Link}.
     */
    protected Link<E> next;

    /**
     * A reference to the previous {@code Link}.
     */
    protected Link<E> prev;

    /**
     * A method that returns a unique integer to represent the {@code Link}.
     *
     * @return an integer representative of the {@code Link}
     */
    @Override
    public int hashCode() {
        return store.hashCode();
    }

    /**
     * A method to check for equality between two {@code Link}s
     *
     * @param other the {@code} Object to be compared
     * @return whether or not the {@code Link}s are equal
     */
    @Override
    @SuppressWarnings("Unchecked")
    public boolean equals(Object other){

        if(this == other) return true;
        else if(!(other instanceof Link)) return false;
        final Link<E> cast = (Link<E>) other;
        return this.store.equals(cast.store);

    }

    /**
     * A method that overrides {@code Object}'s {@code toString()}
     *
     * @return a {@code String} to describe the {@code Link}.
     */
    @Override
    public String toString(){

        return (store != null)? store.toString(): "null";

    }

}
