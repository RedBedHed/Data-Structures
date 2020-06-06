package util;

/**
 * Linked Priority Queue
 *
 * <p>
 * This is a {@code LinkedPriorityQueue} implemented with a {@code LinkedList}.
 * The time complexity of insertion is ~O(log(n)) in the best case. Deletion
 * takes place in constant time. While it may be time efficient, this
 * implementation is not memory efficient and is only meant to be used in
 * situations where this fact is not an issue.
 *
 * @author Ellie Moore
 * @version 05.09.2020
 * @param <E> the type
 */
public class LinkedPriorityQueue<E> implements Queue<E> {

    /**
     * The internal storage of the {@code PriorityQueue}.
     */
    private LinkedList<Element<E>> internal;

    /**
     * A public constructor for a {@code PriorityQueue}.
     */
    public LinkedPriorityQueue(){
        internal = new LinkedList<>();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void insert(final E e){
        internal.add(0, new Element<E>(0, e));
    }

    /**
     * A method to insert the given element into the {@code Queue} at
     * the given priority.
     *
     * @param e the element to be inserted
     * @param priority the priority associated with the given element
     */
    public void insert(final E e, final int priority) {
        internal.add(binarySearch(priority), new Element<>(priority, e));
    }

    /**
     * @inheritDoc
     */
    @Override
    public E delete() {
        return internal.remove(size() - 1).store;
    }

    /**
     * @inheritDoc
     */
    @Override
    public E peek() {
        return internal.get(size() - 1).store;
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

    /*
     * A simple Binary Search algorithm to find the index
     * at which to insert the Element with the given priority.
     */
    private int binarySearch(final int priority){
        int beg = 0;
        int end = size() - 1;
        while ((end - beg) > 1) {
            final int mid = (beg + end) >>> 1;
            final int cmp = Integer.compare(
                    priority, internal.get(mid).priority
            );
            if (cmp < 0) end = mid;
            else if (cmp > 0) beg = mid;
            else return mid;
        }
        while(
                beg < internal.size() &&
                internal.get(beg).priority < priority
        ) beg++;
        return beg;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString(){
        return internal.toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        return internal.toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode(){
        return internal.hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other == null) return false;
        if(!(other instanceof LinkedPriorityQueue)) return false;
        LinkedPriorityQueue<E> cast = (LinkedPriorityQueue<E>) other;
        return internal.equals(cast.internal);
    }

}
