package util;

/**
 * Array Stack
 *
 * <p>This is a {@code Stack} implementation that makes use of an
 * internal {@code ArrayDeque}.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 02-24-20
 * @see ArrayDeque
 */
public class ArrayStack<E> implements Stack<E> {

    /**
     * The internal storage of the {@code Stack}.
     */
    private ArrayDeque<E> internal;

    /**
     * A public constructor to instantiate a new {@code ArrayStack Object}.
     */
    public ArrayStack() {

        internal = new ArrayDeque<>();

    }

    /**
     * {@inheritDoc}
     *
     * <p>Since internal storage is handled by a {@Deque object},
     * no shifting is involved.
     *
     * @param input {@code Element}
     * @see ArrayDeque#insertOnFront(E)
     * @see java.util.Stack#push(E)
     */
    @Override
    public void push(final E input) {

        internal.insertOnFront(input);

    }

    /**
     * {@inheritDoc}
     *
     * <p>Since internal storage is handled by a {@Deque object},
     * no shifting is involved.
     *
     * @return the {@code Element} at the top of the {@code Stack}(removal)
     * @see ArrayDeque#delete()
     * @see java.util.Stack#pop()
     */
    @Override
    public E pop() {

        return internal.delete();

    }

    /**
     * @inheritDoc
     * @see ArrayDeque#peek()
     * @see java.util.Stack#peek()
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

        if (other == this) {
            return true;
        } else if (!(other instanceof ArrayStack)) {
            return false;
        }
        ArrayStack<E> otherArrayStack = (ArrayStack<E>) other;
        return internal.equals(otherArrayStack.internal);

    }

}
