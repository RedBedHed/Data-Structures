package util;

/**
 * Array Queue
 *
 * <p>This is a {@code Queue} implementation that makes use of an
 * internal {@code ArrayDeque}.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 02-24-20
 * @see ArrayDeque
 */
public class ArrayQueue<E> implements Queue<E> {

    /**
     * The internal storage of the {@code Queue}.
     */
    private ArrayDeque<E> internal;

    /**
     * A public constructor to instantiate a new {@code ArrayQueue Object}.
     */
    public ArrayQueue() {
        internal = new ArrayDeque<>();
    }

    /**
     * @inheritDoc
     * @see ArrayDeque#insert(E)
     * @see java.util.Queue#add(E)
     */
    @Override
    public void insert(final E input) {
        internal.insert(input);
    }

    /**
     * @inheritDoc
     * @return the {@code Element} at the front of the {@code Queue}(removal)
     * @see ArrayDeque#delete()
     * @see java.util.Queue#remove()
     */
    @Override
    public E delete() {
        return internal.delete();
    }

    /**
     * @inheritDoc
     * @see ArrayDeque#peek()
     * @see java.util.Queue#peek()
     */
    @Override
    public E peek() {
        return internal.peek();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return internal.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return internal.toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        return internal.toStore();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        return internal.hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    @SuppressWarnings("Unchecked")
    public boolean equals(final Object other) {
        if (other == this) return true;
        else if (!(other instanceof ArrayQueue)) return false;
        ArrayQueue<E> otherEllieStack = (ArrayQueue<E>) other;
        return internal.equals(otherEllieStack.internal);
    }

}
