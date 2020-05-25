package util;

import java.util.Arrays;
import java.util.Random;

/**
 * Priority Queue
 * <p>
 * This is a priority queue implemented with a Max Heap. Insertion and
 * deletion operations are both O(log(n)).
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 05.10.2020
 */
public class PriorityQueue<E> implements Queue<E> {

    /*
     * The initial storage allocation for the heap.
     */
    private static final int ALLOCATION = 100;

    /**
     * The size of the {@code PriorityQueue}.
     */
    private int size;

    /**
     * The internal storage of the {@code PriorityQueue}.
     */
    Element<E>[] heap;

    /**
     * A public constructor for the {@code PriorityQueue}.
     */
    @SuppressWarnings("Unchecked")
    public PriorityQueue() {
        size = 0;
        heap = (Element<E>[]) new Element[ALLOCATION];
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This implementation of {@code Queue#insert(E)} adds the given
     * element at a leaf node with priority 0.
     *
     * @param e the element to be inserted
     */
    @Override
    public void insert(final E e) {
        heap[size] = new Element<>(0, e);
    }

    /**
     * A method that adds the given element to the Queue at the
     * specified priority.
     *
     * @param priority the priority of the element to be inserted
     * @param e        the element to be inserted
     */
    public void insert(final int priority, final E e) {
        if (priority < 0) throw new IllegalArgumentException();
        grow();
        heap[size] = new Element<>(priority, e);
        siftUp(size);
        size++;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This implementation of {@code Queue#delete()} deletes and
     * returns the element with the highest priority.
     *
     * @return the element with the highest priority
     */
    @Override
    public E delete() {
        if (size == 0) return null;
        final Element<E> removal = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 2) siftDown(0);
        else if (size > 1 && heap[0].priority < heap[1].priority)
            swap(0, 1);
        return removal.store;
    }

    /**
     * @inheritDoc
     */
    @Override
    public E peek() {
        if (size == 0) return null;
        return heap[0].store;
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

    /*
     * A method that performs a copy from the current internal Array
     * to a new, larger Array.
     */
    private void grow() {
        if (size >= heap.length - 1)
            heap = Arrays.copyOf(heap, heap.length << 1);
    }

    /*
     * A method to fix the Heap tree after insertion.
     * Starts at the given leaf of the sub-tree to be fixed and
     * works its way upwards.
     */
    private void siftUp(int x) {
        if(x < 1) return;
        int parent;
        while (x > 0 &&
               heap[parent = (x - 1) >>> 1].priority < heap[x].priority) {
            swap(parent, x);
            x = parent;
        }
    }

    /*
     * A method to fix the Heap tree after deletion. Starts
     * at the given root of the sub-tree to be fixed and
     * works its way downwards.
     */
    private void siftDown(int x) {
        int left;
        int right;
        while ((left = (x << 1) + 1) < size &&
               (right = left + 1) < size &&
               (heap[x].priority < heap[left].priority ||
                heap[x].priority < heap[right].priority)){
            if (heap[left].priority < heap[right].priority) {
                swap(x, right);
                x = right;
            } else {
                swap(x, left);
                x = left;
            }
        }
    }

    /*
     * A method to swap the elements at the specified indices in the
     * heap.
     */
    private void swap(int i, int ix) {
        Element<E> e = heap[i];
        heap[i] = heap[ix];
        heap[ix] = e;
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings("Unchecked")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof ArrayList)) return false;
        PriorityQueue<E> cast = (PriorityQueue<E>) other;
        if (cast.size != this.size) return false;
        for (int i = 0; i < size; i++)
            if (!heap[i].equals(cast.heap[i]))
                return false;
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (Element e : heap)
            hash = EllieCollections.HASH_CODE_CONST * hash +
                    (e != null ? e.hashCode() : 0);
        return hash;
    }


    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < size; i++)
            out.append(heap[i]).append((i < size - 1) ? ", " : "");
        return out.append("]").toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < heap.length; i++)
            out.append(heap[i]).append((i < heap.length - 1) ? ", " : "");
        return out.append("]").toString();
    }

}
