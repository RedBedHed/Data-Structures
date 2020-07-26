package util;

/**
 * Array Deque
 *
 * <p>A double-ended {@code Queue} implementation featuring
 * a circular {@code Array}.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 02-20-20
 * @see java.util.ArrayDeque
 */
public class ArrayDeque<E> implements Deque<E> {

    /*
     * An integer constant for initial size and growth of the Deque.
     */
    private static final int INTERNAL_ALLOCATION = 100;

    /**
     * The {@code internal Array} used to store the {@code Object}'s data.
     * The capacity of the {@code Deque Object} is (at all times) the
     * length of this {@code Array}.
     */
    private E[] internal;

    /**
     * The size of the {@code Deque} (the number of elements). This
     * may grow or shrink dynamically with insertions/deletions.
     */
    private int size;

    /**
     * An {@code integer} variable to keep track of the front of the {@code Deque}.
     */
    private int front;

    /**
     * An {@code integer} variable to keep track of the back of the {@code Deque}.
     */
    private int back;

    /**
     * A public {@code constructor} to initialize the {@code internal Array}
     * and set the {@code Deque size} to zero.
     */
    @SuppressWarnings("Unchecked")
    public ArrayDeque() {
        internal = (E[]) new Object[INTERNAL_ALLOCATION];
        back = internal.length;
        front = 0;
        size = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Since the {@code internal Array} is a circular {@code Array}, there is
     * no need for any shifting of its contents. Thus, the method simply mutates
     * the {@code internal Array} at the {@code front} index and increments this
     * index. If (upon returning to its native territory) the {@code front}
     * index reaches the size of the {@code internal Array}, it is set to zero
     * and allowed re-entry into its native territory. This method also grows
     * the {@code internal Array} as needed, and increments the {@code size}
     * each time it is called.
     *
     * @param input {@code Element}
     * @see #grow()
     */
    public void insertOnFront(final E input) {
        grow();
        size++;
        internal[front++] = input;
        if (front >= internal.length) front = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Since the {@code internal Array} is circular, no shifting is
     * involved. If the {@code Deque} is empty, the method returns null. If the
     * {@code front} variable decreases to zero it is set to the length of the
     * {@code internal Array} and allowed to cross over into the {@code back}
     * variable's territory. This method decrements the {@code size} and the
     * {@code front} variable each time it is called.
     *
     * @return the {@code Element} at the front of the {@code Deque}
     */
    public E delete() {
        if (isEmpty()) return null;
        else if (front <= 0) front = internal.length;
        size--;
        return internal[--front];
    }

    /**
     * {@inheritDoc}
     *
     * <p>Since the {@code internal Array} is circular, no shifting is involved.
     * Instead, this method decrements the {@code back} index and mutates the
     * {@code internal Array} at this index. If (upon returning to its native
     * territory) the {@code back} variable reaches a value of zero, then it
     * is set to the length of the {@code internal Array} and allowed to re-enter
     * into it's native territory. The variable {@code size} is incremented each
     * time this method is called, and the {@code internal Array} is allowed to
     * grow when necessary.
     *
     * @param input {@code Element}
     * @see #grow()
     */
    public void insert(final E input) {
        grow();
        size++;
        internal[--back] = input;
        if (back <= 0) back = internal.length;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Since the {@code internal Array} is circular, no shifting is involved. If
     * the {@code Deque} is empty, the method returns null. If the {@code back}
     * variable reaches the length of the {@code internal} array, it is set to zero
     * and allowed to enter the {@code front} variable's territory. This method
     * decrements the {@code size} and increments the {@code back} variable each
     * time it is called.
     *
     * @return the {@code Element} at the back of the {@code Deque}
     */
    public E deleteFromBack() {
        if (isEmpty()) return null;
        else if (back >= internal.length) back = 0;
        size--;
        return internal[back++];
    }

    /**
     * A void method that gives the illusion of dynamic behavior.
     * If {@code size} equals the length of the {@code internal Array}, then this
     * method performs a manual copy to a larger circle-{@code Array}. The method
     * then assigns the new {@code Array}'s reference to the {@code internal}
     * pointer and leaves the old {@code Array} for garbage collection.
     *
     * @see System#arraycopy(Object, int, Object, int, int)
     * @see java.util.Arrays#copyOf(Object[], int)
     */
    @SuppressWarnings("Unchecked")
    private void grow() {
        if (size >= internal.length - 1) {
            final E[] copy = (E[]) new String[internal.length + INTERNAL_ALLOCATION];
            int index = back;
            int secondaryIndex = 0;
            for (int i = 0; i < size; i++) {
                if (index < internal.length)
                    copy[index + INTERNAL_ALLOCATION] = internal[index++];
                else copy[secondaryIndex] = internal[secondaryIndex++];
            }
            back += INTERNAL_ALLOCATION;
            front = (secondaryIndex != 0) ? secondaryIndex : index + INTERNAL_ALLOCATION;
            internal = copy;
        }
    }

    /**
     * A method that is similar to {@code peek()}. However, this method returns the
     * element at the back of the {@code Deque}.
     *
     * @return the element at the back of the {@code Deque}
     */
    public E peekFromBack() {
        if(back < internal.length) return internal[back];
        else if(back == internal.length) return internal[back - 1];
        else throw new IndexOutOfBoundsException();
    }

    /**
     * @inheritDoc
     */
    public E peek() {
        if(front > 0) return internal[front - 1];
        else if(front == 0) return internal[0];
        else throw new IndexOutOfBoundsException();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return size;
    }

    //public int getFront(){return front;}
    //public int getBack(){return back;}

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        int index = back;
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (index == internal.length) {
                index = 0;
            }
            out.append(internal[index++]).append((i < size - 1) ? ", " : "");
        }
        out.append("]");
        return out.toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < internal.length; i++) {
            out.append(internal[i]).append((i < internal.length - 1) ? ", " : "");
        }
        out.append("]");
        return out.toString();
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings("Unchecked")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof ArrayDeque)) return false;
        ArrayDeque<E> otherElement = (ArrayDeque<E>) other;
        if (otherElement.size != this.size) return false;
        for (int i = 0; i < internal.length; i++) {
            if (!internal[i].equals(otherElement.internal[i]))
                return false;
        }
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (E e : internal) {
            hash = EllieCollections.HASH_CODE_CONST * hash +
                    (e != null ? e.hashCode() : 0);
        }
        return hash;
    }

}
