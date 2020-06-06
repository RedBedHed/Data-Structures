package util;

/**
 * Linked Deque
 *
 * <p>A {@code LinkedDeque} is a double-ended {@code Queue} implementation
 * that relies on {@code Link}s for its storage.
 *
 * <p>For small {@code Object}s and boxed primitives, a {@code LinkedDeque}
 * is less efficient than an {@code ArrayDeque}. It requires extra heap space to
 * store {@code Link}s and relies heavily on garbage collection during deletion.
 * For large, expensive {@code Object}s, however, a {@code LinkedDeque} certainly
 * has an advantage, as it doesn't copy or shift its data during growth. Ultimately,
 * it is up to the programmer to decide which {@code Deque} implementation will work
 * best for their program.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-06-2020
 */
public class LinkedDeque<E> implements Deque<E> {

    /**
     * A {@code Link} that represents the front (head) of the Deque.
     */
    private Link<E> head;

    /**
     * A {@code Link} that represents the back (tail) of the Deque.
     */
    private Link<E> tail;

    /**
     * An integer to represent the size of the {@code Deque}.
     */
    private int size;

    /**
     * A public constructor for a {@code LinkedDeque} to initialize the head and
     * tail, linking them to each other. This constructor also sets the {@code size}
     * variable to zero.
     */
    public LinkedDeque() {
        head = new Link<>();
        tail = new Link<>();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>A new link is initialized to replace the head. The element is
     * assigned to the current head's {@code storage} variable. the current
     * head's {@code prev} variable is assigned to the new link. the new link's
     * {@code next} variable is assigned to the current head. The new link
     * is then assigned to the {@code head} variable, effectively positioning
     * it as the new head.
     *
     * @param element the element to be inserted.
     */
    public void insertOnFront(final E element) {
        Link<E> replacement = new Link<>();
        head.store = element;
        head.prev = replacement;
        replacement.next = head;
        head = replacement;
        size++;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method is essentially the inverse of {@code insertOnFront()}
     * A new link is initialized to replace the head. The element is
     * assigned to the current tails's {@code storage} variable. the current
     * tail's {@code next} variable is assigned to the new link. the new link's
     * {@code prev} variable is assigned to the current tail. The new link
     * is then assigned to the {@code tail} variable, effectively positioning
     * it as the new tail.
     *
     * @param element
     * @see LinkedDeque#insertOnFront(E)
     */
    @Override
    public void insert(final E element) {
        Link<E> replacement = new Link<>();
        tail.store = element;
        tail.next = replacement;
        replacement.prev = tail;
        tail = replacement;
        size++;
    }

    /**
     * {@inheritDoc}
     *
     * <p>If the {@code Deque} is empty then this method will return null.
     * If not empty, the method decrements the {@code size variable} and
     * assigns the storage of the last link to a temporary variable.
     * The head then moves to the last link, assigning the old head to null
     * and leaving it to be garbage collected. Finally, the temporary variable's
     * contents are returned.
     */
    @Override
    public E delete() {
        if (isEmpty()) return null;
        size--;
        final E removal = head.next.store;
        head = head.next;
        head.prev = null;
        return removal;
    }

    /**
     * {@inheritDoc}
     *
     * <p>If the {@code Deque} is empty then this method will return null.
     * If not empty, the method decrements the {@code size variable} and
     * assigns the storage of the last link to a temporary variable.
     * The tail then moves to the last link, assigning the old tail to null
     * and leaving it to be garbage collected. Finally, the temporary variable's
     * contents are returned.
     *
     * @return the element at the back of the {@code Deque}
     */
    public E deleteFromBack() {
        if (isEmpty()) return null;
        size--;
        final E removal = tail.prev.store;
        tail = tail.prev;
        tail.next = null;
        return removal;
    }

    /**
     * @inheritDoc
     */
    @Override
    public E peek() {
        return head.next.store;
    }

    /**
     * A method that allows the client to peek at the back of the deque.
     *
     * @return the element at the back of the Deque
     */
    public E peekFromBack() {
        return tail.prev.store;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder("[");
        int i = 0;
        for (Link<E> link = head.next; link.next != null; link = link.next) {
            out.append(link);
            if (++i < size) {
                out.append(", ");
            }
        }
        return out.append("]").toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        final StringBuilder out = new StringBuilder("[");
        int i = 0;
        for (Link<E> link = head; link != null; link = link.next) {
            out.append(link.store);
            if (link.next != null) {
                out.append(", ");
            }
        }
        return out.append("]").toString();
    }


    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (Link<E> link = head.next; link.next != null; link = link.next) {
            hash = EllieCollections.HASH_CODE_CONST * hash +
                    (link.store != null ? link.hashCode() : 0);
        }
        return hash;
    }

    /**
     * @inheritDoc
     */
    @Override
    @SuppressWarnings("Unchecked")
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof LinkedDeque)) return false;
        LinkedDeque<E> cast = (LinkedDeque<E>) other;
        if (cast.size != this.size) return false;
        for (Link<E> link = head.next, otherLink = cast.head.next;
             link.next != null && otherLink.next != null;
             link = link.next, otherLink = otherLink.next) {
            if (!link.equals(otherLink)) return false;
        }
        return true;
    }

}
