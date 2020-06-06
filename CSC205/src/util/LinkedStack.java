package util;

/**
 * Linked Stack
 *
 * <p>A {@code LinkedStack} is a {@code Stack} that uses {@code Link}s
 * to store its data.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-06-2020
 * @see LinkedDeque
 */
public class LinkedStack<E> implements Stack<E> {

    /**
     * The internal storage of the {@code Stack}
     */
    private LinkedDeque<E> internal;

    /**
     * A public constructor for a stack that initializes the
     * internal {@code Deque}
     */
    public LinkedStack() {
        internal = new LinkedDeque<>();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method creates a new {@code Link} and attaches it to the front
     * of the {@code internal Deque}.
     *
     * @param element the element being pushed
     * @see LinkedDeque#insertOnFront(E)
     */
    @Override
    public void push(final E element) {
        internal.insertOnFront(element);
    }

    /**
     * {@inheritDoc}
     *
     * <P>If the {@code Stack} is empty, the method returns null. Otherwise,
     * the top element is deleted and returned. This method then sets the front
     * {@code Link}'s reference to null, leaving the {@code Link} for garbage
     * collection.
     *
     * @return the element at the top of the {@code Stack}
     * @see LinkedDeque#delete()
     */
    @Override
    public E pop() {
        return internal.delete();
    }

    /**
     * @inheritDoc
     */
    @Override
    public E peek() {
        return null;
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
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof LinkedStack)) return false;
        LinkedStack<E> cast = (LinkedStack<E>) other;
        return internal.equals(cast.internal);
    }

}
