package util;

/**
 * Linked Queue
 *
 * <p>A {@code Linked Queue} is a {@code Queue} that stores it's data in
 * {@code Link}s
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-07-2020
 * @see LinkedDeque
 */
public class LinkedQueue<E> implements Queue<E> {

    /**
     * The internal storage of the {@code Queue}
     */
    private LinkedDeque<E> internal;

    /**
     * A public constructor for a {@code LinkedQueue} that
     * initializes the internal {@code Deque}.
     */
    public LinkedQueue() {
        internal = new LinkedDeque<>();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method inserts an item at the back of the {@code Queue}
     * By attaching a new {@code Link}.
     *
     * @param element the element to be inserted
     * @see LinkedDeque#insert(E)
     */
    @Override
    public void insert(final E element) {
        internal.insert(element);
    }

    /**
     * {@inheritDoc}
     *
     * <P>If the {@code Queue} is empty, the method returns null. Otherwise,
     * the top element is deleted and returned. This method then sets the front
     * {@code Link}'s reference to null, leaving the {@code Link} for garbage
     * collection.
     *
     * @return the element at the front of the {@code Queue}
     * @see LinkedDeque#delete()
     */
    @Override
    public E delete() {
        return internal.delete();
    }

    /**
     * @inheritDoc
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
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof LinkedQueue)) return false;
        LinkedQueue<E> cast = (LinkedQueue<E>) other;
        return internal.equals(cast.internal);
    }

}
