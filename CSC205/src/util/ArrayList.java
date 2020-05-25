package util;

import java.util.Arrays;

/**
 * Array List
 *
 * <p>
 * This is a simplified List implementation!
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 02-18-20
 */
public class ArrayList<E> implements List<E> {

    /*
     * An integer constant representative of both the initial
     * storage and the growth factor of the internal Array.
     */
    private static final int ALLOCATION = 100;

    /**
     * An internal {@code Array} of elements to hold the
     * {@code ArrayList}'s data. The storage capacity of the
     * {@code ArrayList} is (at all times) the length of this
     * {@code Array}.
     */
    private E[] internal;

    /**
     * A variable to represent the size of the {@code ArrayList}.
     */
    private int size;

    /**
     * A public constructor to initialize an empty {@code ArrayList}.
     * (size zero with an allocation of 50 null indexes)
     */
    @SuppressWarnings("Unchecked")
    public ArrayList() {

        internal = (E[]) new Object[ALLOCATION];
        size = 0;

    }

    /**
     * A secondary constructor for a {@code ArrayList} which takes an
     * element array as a parameter.
     *
     * @param array the array to be converted.
     */
    public ArrayList(final E[] array){

        this();
        for(final E e: array) add(e);

    }

    /**
     * @inheritDoc
     */
    @Override
    public E get(final int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Argument must be a valid index"
            );
        }
        return internal[index];

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If the index isn't in range an {@code Exception} is thrown.
     * The entire internal {@code Array} is down-shifted by one and
     * size is decremented each time this method is called.
     */
    @Override
    public E remove(int index) {

        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException(
                    "Argument must be a valid index"
            );
        }
        final E selection = internal[index];
        System.arraycopy(
                internal, index + 1, internal, index, size - index
        );
        size--;
        return selection;

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * For this implementation of {@code List#remove(E)} The entire
     * {@code internal Array} is down-shifted by one and size is decremented
     * each time this method is called.
     *
     * @param input the element to remove
     * @return the contents of the array at the specified index
     */
    @Override
    public boolean remove(E input) {

        final int index = indexOf(input);
        if(index >= size || index < 0) return false;
        else {
            remove(index);
            return true;
        }

    }

    /**
     * A method to remove all occurrences of an element from the
     * {@code List}.
     *
     * @param input the element to be removed
     * @return whether or not the element was removed successfully
     */
    public boolean removeAll(E input){

        boolean flag = false;
        if(input == null){
            for(int i = 0; i < size; i++){
                if(internal[i] == null){
                    remove(i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < size; i++){
                if(internal[i] != null && internal[i].equals(input)){
                    remove(i);
                    flag = true;
                }
            }
        }
        return flag;

    }

    /**
     * @inheritDoc
     */
    @Override
    public E set(final int index, final E input) {

        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException(
                    "Argument must be a valid index"
            );
        }
        final E temp = internal[index];
        internal[index] = input;
        return temp;

    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean replace(final E candidate, final E input) {

        final int index = indexOf(candidate);
        if(index >= size || index < 0) return false;
        else {
            set(index, input);
            return true;
        }

    }

    /**
     * A method to replace all occurrences of the given candidate
     * element.
     *
     * @param candidate the element to be replaced
     * @param input the element to replace with
     * @return whether or not the element was replaced successfully
     */
    public boolean replaceAll(final E candidate, final E input){

        boolean flag = false;
        if(input == null){
            for(int i = 0; i < size; i++) {
                if(internal[i] == null) {
                    set(i, input);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < size; i++) {
                if(internal[i] != null
                    && internal[i].equals(candidate)) {
                    set(i, input);
                    flag = true;
                }
            }
        }
        return flag;

    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<E> add(E input) {

        add(size, input);
        return this;

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This method pushes each preceding element up by one to make
     * room for the insertion and allocates new space beforehand
     * (as needed).
     */
    @Override
    public void add(final int index, final E input) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Argument must be a valid index"
            );
        }
        grow();
        System.arraycopy(
                internal, index, internal, index + 1, size - index
        );
        internal[index] = input;
        size++;

    }

    /*
     * A method that performs a copy from the current
     * internal Array to a new, larger Array.
     */
    private void grow() {

        if (size >= internal.length - 1) {
            internal = Arrays.copyOf(
                    internal, internal.length + ALLOCATION
            );
        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This method makes use of the {@code Element}'s
     * equals method.
     */
    @Override
    public int indexOf(final E input) {

        for (int i = 0; i < size; i++) {
            if (internal[i].equals(input)) {
                return i;
            }
        }
        return -1;

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

        StringBuilder out = new StringBuilder();
        out.append("[");
        for (int i = 0; i < size; i++) {
            out.append(internal[i]).append(((i < size - 1) ? ", " : ""));
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
        if(other == null) return false;
        if (!(other instanceof ArrayList)) return false;
        ArrayList<E> otherElement = (ArrayList<E>) other;
        if (otherElement.size != this.size) return false;
        for (int i = 0; i < size; i++) {
            if (!internal[i].equals(otherElement.internal[i])) {
                return false;
            }
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

    /**
     * @inheritDoc
     */
    @Override
    public Object[] toArray() {

        Object[] out = new Object[size];
        System.arraycopy(internal, 0, out, 0, size);
        internal = (E[]) new Object[ALLOCATION];
        size = 0;
        return out;

    }

}
